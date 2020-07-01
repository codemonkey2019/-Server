package com.clouddisk.server.dispatcher;

import com.clouddisk.server.communication.MessageBody;
import com.clouddisk.server.controller.UserManagerController;
import com.clouddisk.server.util.SocketConnect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

@Component
@Scope("prototype")
@Slf4j
public class UserManagerDispatcher {
    @Autowired
    private UserManagerController userManagerController;

    private boolean flag = false;

    public void dispatch(Socket socket) {
        while (!flag) {
            if (socket.isClosed()) {//如果链接断开，则退出线程
                log.info("连接断开");
                break;
            }
            MessageBody messageBody = SocketConnect.getMessageBodyFromClient(socket);
            log.info(messageBody.toString());
            if (messageBody != null && "/login".equals(messageBody.getPath())) {
                flag = userManagerController.login(socket, messageBody);
                if (flag) {
                    log.info("登录成功");
                }
            } else if (messageBody != null && "/regist".equals(messageBody.getPath())) {
                userManagerController.regist(socket, messageBody);
            } else if (messageBody != null && "/close".equals(messageBody.getPath())) {
                try {
                    if (socket != null) {
                        socket.close();
                        log.info("连接已断开");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                log.info("socket非正常关闭");
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
