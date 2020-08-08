package com.clouddisk.server.controller;

import com.clouddisk.server.communication.MessageBody;
import com.clouddisk.server.communication.request.LoginRequest;
import com.clouddisk.server.communication.request.RegistRequest;
import com.clouddisk.server.communication.response.LoginAnswer;
import com.clouddisk.server.communication.response.RegistAnswer;
import com.clouddisk.server.efficientsearch.KFNodeCache;
import com.clouddisk.server.efficientsearch.KFNodeCacheManager;
import com.clouddisk.server.service.UserManagerService;
import com.clouddisk.server.thread.ConnectedUser;
import com.clouddisk.server.thread.UserManager;
import com.clouddisk.server.util.InformationCast;
import com.clouddisk.server.util.SocketConnect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.Socket;

/**
 * 1、请求路由
 * 2、数据格式转换
 * 3、应答信息返回
 * 4、UserManager中添加记录
 */
@Component
@Scope("prototype")
public class UserManagerController {
    @Autowired
    private UserManager userManager;
    @Autowired
    private UserManagerService userManagerService;
    @Autowired
    private KFNodeCacheManager kfNodeCacheManager;

    @RequestPath("/login")
    public boolean login(Socket socket, MessageBody messageBody){
        System.out.println(messageBody);
        LoginRequest request = InformationCast.messageBodyToRequestBody(messageBody,LoginRequest.class);
        LoginAnswer answer =userManagerService
                .login(request);
        //缓存中加一个,创建必要的文件夹
        if (answer.getSuccess()) {
            File f1 = new File("C:/MyCloudDisk/server/"+request.getUserName()+"/SMFileCache/");
            if (!f1.exists()) {
                f1.mkdirs();
            }
            File f2 = new File("C:/MyCloudDisk/server/"+request.getUserName()+"/KFcache/");
            if (!f2.exists()) {
                f2.mkdirs();
            }
            kfNodeCacheManager.addKFNodeCacheByUserName(request.getUserName(),new KFNodeCache());//在内存中为该用户读取它的EDB
            kfNodeCacheManager.loadCache(request.getUserName());//装载该用户的EDB
            userManager.add(request.getUserName()
                    ,new ConnectedUser(request.getUserName(),request.getPassword(),socket));
        }
        //将应答信息返回
        SocketConnect.sendAnwswerToClient(socket, answer);
        return answer.getSuccess();
    }
    @RequestPath("/regist")
    public boolean regist(Socket socket, MessageBody messageBody){
        RegistRequest request = InformationCast.messageBodyToRequestBody(messageBody, RegistRequest.class);
        RegistAnswer answer= userManagerService.regist(request);
        SocketConnect.sendAnwswerToClient(socket, answer);
        return answer.getSuccess();
    }
}
