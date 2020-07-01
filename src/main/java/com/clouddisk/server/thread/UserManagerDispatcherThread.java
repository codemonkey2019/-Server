package com.clouddisk.server.thread;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
//用用户管管理的线程类，用于处理登录注册请求
@Component
@Scope("prototype")
public class UserManagerDispatcherThread extends Thread {
    @Autowired
    @Getter
    private UserManagerDispatcherRun userManagerDispatcherRun;

    @Override
    public void run() {
        userManagerDispatcherRun.run();
    }
}
