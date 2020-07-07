package com.clouddisk.server.efficientsearch;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KFNodeCacheManager {
    private Map<String,KFNodeCache> kfNodeCacheManager = new ConcurrentHashMap<>();

    /**
     * 添加一个KDNodeCache
     * @param userName
     * @param kfNodeCache
     */
    public void addKFNodeCacheByUserName(String userName, KFNodeCache kfNodeCache){
        this.kfNodeCacheManager.put(userName,kfNodeCache);
    }

    /**
     * 获取指定的KFNodeCache
     * @param userName
     * @return
     */
    public KFNodeCache getKFNodeCacheByUserName(String userName){
        return this.kfNodeCacheManager.get(userName);
    }
    /**
     * 为指定的KFNodeCache中添加一个KFNode
     * @param userName
     * @return
     */
    public void addKFNodeByUserName(String userName, KFNode kfNode){
        this.kfNodeCacheManager.get(userName).addKFNode(kfNode);
    }

    /**
     * 获取指定的KFNodeCache中的一个指定的节点
     * @param userName
     * @param key
     * @return
     */
    public Node getNodeByUserNameAndKey(String userName,String key){
        return this.kfNodeCacheManager.get(userName).getNode(key);
    }

    /**
     * 获取指定用户的KFNodeCache
     *
     * @param userName
     */
    public void loadCache(String userName){
        File cache = new File("C:/MyCloudDisk/server/"+userName+"/KFcache/KFNodeCache.cache");
        if (!cache.exists()){
            return;
        }
        BufferedReader br=null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(cache)));
            String KFNodeJson;
            while(true){
                String KFNodeJSON = br.readLine();
                if ((KFNodeJSON=br.readLine())!=null){
                    addKFNodeByUserName(userName, JSON.parseObject(KFNodeJSON,KFNode.class));
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
     * 将一个KFNode写入指定缓存文件
     * @param kfNode
     */
    public synchronized void persistKFNodeByUserName(String userName, KFNode kfNode){
        File cache = new File("C:/MyCloudDisk/server/"+userName+"/KFcache/KFNodeCache.cache");
        BufferedWriter bw = null;
        try {
            //追加写入
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
