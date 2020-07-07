package com.clouddisk.server.controller;

import com.clouddisk.server.communication.MessageBody;
import com.clouddisk.server.communication.request.DownLoadRequest;
import com.clouddisk.server.communication.request.SearchRequest;
import com.clouddisk.server.communication.request.UpdateRequest;
import com.clouddisk.server.communication.response.SearchAnswer;
import com.clouddisk.server.communication.response.UpdateAnswer;
import com.clouddisk.server.service.FileManagerService;
import com.clouddisk.server.thread.ConnectedUser;
import com.clouddisk.server.util.InformationCast;
import com.clouddisk.server.util.SocketConnect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Slf4j
@Component@Scope("prototype")
public class FileManagerController {
    @Autowired
    private FileManagerService fileManagerService;
    @RequestPath("/update")
    public void update(ConnectedUser connectedUser, MessageBody messageBody){
        //将message的请求信息拿出来
        UpdateRequest request = InformationCast.messageBodyToRequestBody(messageBody,UpdateRequest.class);
        //传给fileManagerService处理
        UpdateAnswer answer= fileManagerService.updateFile(request, connectedUser);
        //给客户端返回数据
        SocketConnect.sendAnwswerToClient(connectedUser.getSocket(),answer);
    }

    public void search(ConnectedUser connectedUser, MessageBody messageBody) {
        /**
         *  private String tw;
         *     private String stc;
         *     private Set<String> D;
         */
        //将message的请求信息拿出来
        SearchRequest request = InformationCast.messageBodyToRequestBody(messageBody,SearchRequest.class);
        //传给fileManagerService处理
        SearchAnswer answer = fileManagerService.search(request, connectedUser.getUserName());
        log.info(answer.toString());
        //给客户端返回数据
        SocketConnect.sendAnwswerToClient(connectedUser.getSocket(),answer);
    }

    public void searchAll(ConnectedUser connectedUser) {
        SearchAnswer answer = fileManagerService.searchAll(connectedUser.getUserName());
        log.info(answer.toString());
        //给客户端返回数据
        SocketConnect.sendAnwswerToClient(connectedUser.getSocket(),answer);
    }

    public void download(ConnectedUser connectedUser, MessageBody messageBody) {
        DownLoadRequest request = InformationCast.messageBodyToRequestBody(messageBody,DownLoadRequest.class);
        fileManagerService.sendFiles(request.getSelectedFiles(),connectedUser.getSocket(),connectedUser.getUserName());
    }
}
