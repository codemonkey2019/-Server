package com.clouddisk.server.thread;

import com.clouddisk.server.dispatcher.UserManagerDispatcher;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.Socket;

@Component
@Scope("prototype")
public class UserManagerDispatcherRun implements Runnable {
    @Setter
    private Socket socket;
    @Autowired
    private UserManagerDispatcher userManagerDispatcher;
    @Override
    public void run() {
        userManagerDispatcher.dispatch(this.socket);
    }
}
