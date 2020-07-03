package com.clouddisk.server.efficientsearch;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class CacheManager {
    @Autowired
    private KFNodeCache kfNodeCache;

    /**
     * 从缓存文件中加载所有KFNode
     */
    public void loadCache(){
        File cache = new File(KFNodeCache.CACHE_PATH);
        BufferedReader br=null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(cache)));
            String KFNodeJson;
            while(true){
                String KFNodeJSON = br.readLine();
                if ((KFNodeJSON=br.readLine())!=null){
                    kfNodeCache.addKFNode(JSON.parseObject(KFNodeJSON,KFNode.class));
                }else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 将一个KFNode写入缓存文件
     * @param kfNode
     */
    public synchronized void persistKFNode(KFNode kfNode){
        File cache = new File(KFNodeCache.CACHE_PATH);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cache,true)));
            String KFNodeJson = JSON.toJSONString(kfNode);
            bw.write(KFNodeJson);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (bw!=null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
