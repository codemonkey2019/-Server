package com.clouddisk.server.service.impl;

import com.clouddisk.server.communication.request.LoginRequest;
import com.clouddisk.server.communication.request.RegistRequest;
import com.clouddisk.server.communication.response.LoginAnswer;
import com.clouddisk.server.communication.response.RegistAnswer;
import com.clouddisk.server.dao.UserDao;
import com.clouddisk.server.entity.User;
import com.clouddisk.server.service.UserManagerService;
import com.clouddisk.server.util.InformationCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userManagerService")
public class UserManagerServiceImpl implements UserManagerService {
    @Autowired
    private UserDao userDao;
    @Override
    @Transactional
    public LoginAnswer login(LoginRequest loginRequest) {
        User user = InformationCast.loginRequestToUser(loginRequest);
        //执行登录
        boolean success = userDao.login(user);
        //构造回答信息
        LoginAnswer answer = new LoginAnswer(success);
        return answer;
    }

    @Override
    @Transactional
    public RegistAnswer regist(RegistRequest request) {
        User user = InformationCast.loginRequestToUser(request);
        //执行注册
        boolean success = userDao.regist(user);
        //构造应答信息
        RegistAnswer answer = new RegistAnswer(success);
        //返回应答信息
        return answer;
    }
}
