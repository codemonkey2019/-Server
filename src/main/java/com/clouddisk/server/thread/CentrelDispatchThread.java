package com.clouddisk.server.thread;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author L
 * @date 2019-09-22 21:05
 * @desc
 **/
@Component
@Scope("prototype")
public class CentrelDispatchThread extends Thread {

    @Autowired
    @Getter
    private CentrelDispatchRun centrelDispatchRun;

    @Override
    public void run() {
        centrelDispatchRun.run();
    }
}
