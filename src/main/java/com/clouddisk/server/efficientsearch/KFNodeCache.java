package com.clouddisk.server.efficientsearch;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class KFNodeCache {
    public static final String CACHE_PATH="C:/MyCloudDisk/cache/KFNode.cache";
    private Map<String, Node> KFNodeCache = new ConcurrentHashMap<>();

    public void addKFNode(KFNode kfNode){
        this.KFNodeCache.put(kfNode.getU(),kfNodeToNode(kfNode));
    }
    private Node kfNodeToNode(KFNode kfNode){
        return new Node(kfNode.getE(),kfNode.getCind());
    }

    public List<KFNode> getAllKFNodes(){
        List<KFNode> kfNodes = new ArrayList<>();
        KFNode kfNode=new KFNode();
        KFNodeCache.entrySet().forEach(e->{
            kfNode.setU(e.getKey());
            kfNode.setE(e.getValue().getE());
            kfNode.setCind(e.getValue().getCind());
            kfNodes.add(kfNode);
        });
        return kfNodes;
    }
    public Node getNode(String u){
        return KFNodeCache.get(u);
    }
}
