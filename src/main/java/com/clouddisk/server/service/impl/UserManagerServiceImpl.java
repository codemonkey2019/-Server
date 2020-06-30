package com.clouddisk.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.clouddisk.server.communication.request.LoginRequest;
import com.clouddisk.server.communication.response.LoginAnswer;
import com.clouddisk.server.dao.UserDao;
import com.clouddisk.server.entity.User;
import com.clouddisk.server.service.UserManagerService;
import com.clouddisk.server.util.InformationCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userManagerService")
public class UserManagerServiceImpl implements UserManagerService {
    @Autowired
    private UserDao userDao;
    @Override
    public String login(LoginRequest loginRequest) {
        User user = InformationCast.loginRequestToUser(loginRequest);
        //执行登录
        boolean success = userDao.login(user);
        //构造回答信息
        LoginAnswer answer = new LoginAnswer();
        answer.setSuccess(success);
        return JSON.toJSONString(answer);
    }
}
