package com.clouddisk.server.thread;

import com.clouddisk.server.config.ApplicationContextProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用于为连接缓存中的每个连接创建一个线程去处理的线程
 */
@Slf4j
@Component
public class UserManagerCacheThread extends Thread{
    @Autowired
    private UserManager userManager;
    @Override
    public void run() {
        while (true){
            ConnectedUser one = userManager.getOne();
            log.info("Cache线程获取一个用户连接："+one.getUserName());
            CentrelDispatchThread centrelDispatchThread = ApplicationContextProvider.getBean(CentrelDispatchThread.class);
            centrelDispatchThread.getCentrelDispatchRun().setConnectedUser(one);
            centrelDispatchThread.start();
        }
    }
}
