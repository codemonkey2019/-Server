package com.clouddisk.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.clouddisk.server.communication.request.SearchRequest;
import com.clouddisk.server.communication.request.UpdateRequest;
import com.clouddisk.server.communication.response.SearchAnswer;
import com.clouddisk.server.communication.response.UpdateAnswer;
import com.clouddisk.server.efficientsearch.*;
import com.clouddisk.server.service.FileManagerService;
import com.clouddisk.server.util.SocketConnect;
import com.cryptotool.digests.MyDigest;
import com.cryptotool.util.HexUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component("fileManagerService")
public class FileManagerServiceImpl implements FileManagerService {
    @Value("${fileFolder}")
    private String fileFolder;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private KFNodeCache kfNodeCache;
    @Autowired
    private MyDigest sm3Digist;

    @Override
    public UpdateAnswer updateFile(UpdateRequest updateRequest, Socket socket) {
        //存储updateRequest中所有的KFNode到缓存中
        List<KFNode> kfNodes = updateRequest.getKfNodes();
        kfNodes.forEach(e->{
            kfNodeCache.addKFNode(e);
        });
        //从客户端读取文件
        boolean a = SocketConnect.receiveFile(fileFolder+updateRequest.getFileName(), socket);
        //如果文件读取成功，则把所有新增的KFNode存到文件中
        kfNodes.forEach(e->{
            cacheManager.persistKFNode(e);
        });
        //如果都成功了，则返回true
        UpdateAnswer answer = new UpdateAnswer(a);
        return answer;
    }

    @Override
    public SearchAnswer search(SearchRequest request) {
        //执行搜索算法拿到搜索结果
        List<String> files = searchEDB(request);
        //先过滤一遍不存在的文件
        File file =null;
        for (int i = 0; i < files.size(); i++) {
            file = new File(fileFolder+files.get(i));
            if (!file.exists()) {
                files.remove(i);
            }
        }
        //把结果封装返回
        SearchAnswer answer = new SearchAnswer();
        boolean success = false;
        if (files.size()!=0){
            success=true;
        }
        answer.setSuccess(success);
        answer.setFileNames(files);
        return answer;
    }

    @Override
    public SearchAnswer searchAll() {
        boolean success = false;
        String rootPath = fileFolder;
        List<String> files = new ArrayList<>();
        File file1 = new File(rootPath);
        if (file1.exists()) {
            success = true;
            File[] eFile = file1.listFiles();
            for(File f: eFile){
                if (f.isFile()) {
                    files.add(f.getName());
                }
            }
        }
        return new SearchAnswer(success,files);
    }

    @Override
    public void sendFiles(List<String> fileNames, Socket socket) {
        System.out.println(fileNames);
        fileNames.forEach(f->{
            String path = fileFolder+f;
            SocketConnect.sendFileToClient(socket,path);
        });
    }

    private List<String> searchEDB(SearchRequest request) {
        List<String> files = new ArrayList<>();
        String tw = request.getTw();
        String stc = request.getStc();
        Set<String> D = request.getD();
        String u=null;
        Set<String> checks=new HashSet<>();
        while(stc!=null){
            u= sm3Digist.getDigest(tw+stc);
            Node node = kfNodeCache.getNode(u);
            StateAndInd stateAndInd = hashOplusHexGetStateAndInd(u, node.getE());
            for(String dj: D){
                checks.add(sm3Digist.getDigest(stc+dj));
            }
            if (node.getCind().containsAll(checks)){
                files.add(stateAndInd.getInd());
            }
            stc=stateAndInd.getPreState();
        }
        return files;
    }
    private StateAndInd hashOplusHexGetStateAndInd(String hashState, String hexString) {
        byte[] bytes = HexUtils.hexStringToBinary(hexString);
        byte b = hashState.getBytes()[0];
        byte[] outByte = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byte a = (byte) (bytes[i] ^ b);
            outByte[i]=a;
        }
        String json = new String(outByte);
        return JSON.parseObject(json,StateAndInd.class);
    }
    private String getPseo(String key,String data){
        return sm3Digist.getDigest(key+data);
    }


}
