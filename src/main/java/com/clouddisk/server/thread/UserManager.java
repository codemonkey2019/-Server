package com.clouddisk.server.thread;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;

@Component
public class UserManager {
//    private Map<String,ConnectedUser> connectedUsers = new ConcurrentHashMap<>();
    private LinkedBlockingDeque<ConnectedUser> connectedUsers = new LinkedBlockingDeque<>(500);
    //向缓存中放一个
    public void add(String key, ConnectedUser connectedUser){
        try {
            connectedUsers.put(connectedUser);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //从缓存中拿出一个处理，然后删除缓存记录
    public ConnectedUser getOne(){
        ConnectedUser connectedUser=null;
        try {
            connectedUser = connectedUsers.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connectedUser;
    }
}
