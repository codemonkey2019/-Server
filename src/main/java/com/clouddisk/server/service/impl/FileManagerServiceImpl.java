package com.clouddisk.server.service.impl;

import com.clouddisk.server.communication.request.UpdateRequest;
import com.clouddisk.server.communication.response.UpdateAnswer;
import com.clouddisk.server.efficientsearch.CacheManager;
import com.clouddisk.server.efficientsearch.KFNode;
import com.clouddisk.server.efficientsearch.KFNodeCache;
import com.clouddisk.server.service.FileManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
@Slf4j
@Component("fileManagerService")@Scope("prototype")
public class FileManagerServiceImpl implements FileManagerService {
    @Value("${fileFolder}")
    private String fileFolder;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private KFNodeCache kfNodeCache;
    @Override
    public UpdateAnswer updateFile(UpdateRequest updateRequest, Socket socket) {
        //存储updateRequest中所有的KFNode到缓存中
        List<KFNode> kfNodes = updateRequest.getKfNodes();
        kfNodes.forEach(e->{
            kfNodeCache.addKFNode(e);
        });
        //从客户端读取文件
        boolean a = receiveFile(updateRequest.getFileName(), socket);
        //如果文件读取成功，则把所有新增的KFNode存到文件中
        kfNodes.forEach(e->{
            cacheManager.persistKFNode(e);
        });
        //如果都成功了，则返回true
        UpdateAnswer answer = new UpdateAnswer(a);
        return answer;
    }
    private boolean receiveFile(String fileName, Socket socket){
        File file = new File(fileFolder+"/"+ UUID.randomUUID().toString()+"_"+fileName);
        BufferedOutputStream bos=null;
        BufferedInputStream bis = null;
        try {
            bis=new BufferedInputStream(socket.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] b = new byte[1024];
            int len=0;
            while ((len=bis.read(b))!=-1){
                bos.write(b,0,len);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                }
            }
            if (bos!=null){
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
            log.info("接收文件失败");
            return false;
        }

    }
}
