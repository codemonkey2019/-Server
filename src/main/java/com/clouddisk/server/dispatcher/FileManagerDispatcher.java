package com.clouddisk.server.dispatcher;

import com.clouddisk.server.communication.MessageBody;
import com.clouddisk.server.controller.FileManagerController;
import com.clouddisk.server.entity.User;
import com.clouddisk.server.thread.ConnectedUser;
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
public class FileManagerDispatcher {
    @Autowired
    private FileManagerController fileManagerController;
    private User user;
    private Socket socket;
    private boolean flag=true;
    public void dispatch(ConnectedUser connectedUser){
        this.user=new User(null,connectedUser.getUserName(),connectedUser.getPassword());
        this.socket=connectedUser.getSocket();
        while (flag){
            if (this.socket.isClosed()) {
                log.info("用户退出"+user.getUserName());
                flag=false;
            }
            MessageBody messageBody = SocketConnect.getMessageBodyFromClient(this.socket);
            String path = messageBody.getPath();
            switch (path){
                case "/close":{
                    log.info("用户关闭连接:"+user.getUserName());
                    try {
                        if (socket!=null){
                            this.socket.close();
                            log.info("连接已关闭:"+user.getUserName());
                        }
                    } catch (IOException e) {
                        log.info("连接关闭异常:"+user.getUserName());
                    }
                    break;
                }
                case "/update":{
                    log.info("接收的数据："+messageBody);
                    fileManagerController.update(connectedUser,messageBody);
                    break;
                }
                case "/search":{
                    log.info("接收的数据："+messageBody);
                    fileManagerController.search(connectedUser, messageBody);
                    break;
                }
                case "/download":{
                    log.info("接收的数据："+messageBody);
                    fileManagerController.download(connectedUser,messageBody);
                    break;
                }
                case "/searchAll":{
                    log.info("接收的数据："+messageBody);
                    fileManagerController.searchAll(connectedUser);
                    break;
                }
                case "null":{
                    try {
                        socket.close();
                    } catch (IOException e) {
                        log.info("连接非正常关闭");
                    }
                    break;
                }
                default:{
                    break;
                }
            }
        }
    }
}
