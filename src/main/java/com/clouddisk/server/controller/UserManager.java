package com.clouddisk.server.controller;

import com.clouddisk.server.communication.request.LoginRequest;
import com.clouddisk.server.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserManager {
    @Autowired
    private UserManagerService userManagerService;
    @RequestMapping("/login")
    public String login(LoginRequest loginRequest){
        System.out.println("请求进入"+loginRequest);
        return userManagerService.login(loginRequest);
    }
    @RequestMapping("/regist")
    public String regist(@RequestParam("userInfoJson") String userInfoJson){
        return null;
    }
}
