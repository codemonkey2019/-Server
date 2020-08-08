package com.clouddisk.server.efficientsearch;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代表一个用户的Server端的EDB
 */
public class KFNodeCache {
    private Map<String, Node> KFNodeCache = new HashMap<>();

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
