package com.clouddisk.server.thread;

import com.clouddisk.server.dispatcher.FileManagerDispatcher;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Slf4j
public class CentrelDispatchRun implements Runnable{
    @Setter
    private ConnectedUser connectedUser;
    @Autowired
    private FileManagerDispatcher dispatcher;
    @Override
    public void run() {
        dispatcher.dispatch(connectedUser);
    }
}
