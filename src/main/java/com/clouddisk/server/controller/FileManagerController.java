package com.clouddisk.server.controller;

import com.clouddisk.server.communication.MessageBody;
import com.clouddisk.server.communication.request.UpdateRequest;
import com.clouddisk.server.communication.response.UpdateAnswer;
import com.clouddisk.server.service.FileManagerService;
import com.clouddisk.server.util.InformationCast;
import com.clouddisk.server.util.SocketConnect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.Socket;

@Component@Scope("prototype")
public class FileManagerController {
    @Autowired
    private FileManagerService fileManagerService;
    @RequestPath("/update")
    public void update(Socket socket, MessageBody messageBody){
        //将message的请求信息拿出来
        UpdateRequest request = InformationCast.messageBodyToRequestBody(messageBody,UpdateRequest.class);
        //传给fileManagerService处理
        UpdateAnswer answer= fileManagerService.updateFile(request, socket);
        //给客户端返回数据
        SocketConnect.sendAnwswerToClient(socket,answer);
    }
}
