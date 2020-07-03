package com.clouddisk.server.thread;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserManager {
    private Map<String,ConnectedUser> connectedUsers = new ConcurrentHashMap<>();

    //向缓存中放一个
    public synchronized void add(String key, ConnectedUser connectedUser){
            connectedUsers.put(key,connectedUser);
            if (connectedUsers.size()==1) {
                try {
                    notifyAll();
                } catch (Exception e) {
                }
            }
    }
    //从缓存中拿出一个处理，然后删除缓存记录
    public synchronized ConnectedUser getOne(){
            if(connectedUsers.size()==0){
                try {
                    wait();
                } catch (InterruptedException e) {}
            }
            //connectedUsers中拿出一个
            return pop();
    }
    //拿出一个并删除
    private  ConnectedUser pop(){
        Map.Entry<String, ConnectedUser> next = connectedUsers.entrySet().iterator().next();
        connectedUsers.remove(next.getKey());
        return next.getValue();
    }
}
