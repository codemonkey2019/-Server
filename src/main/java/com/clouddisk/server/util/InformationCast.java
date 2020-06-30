package com.clouddisk.server.util;

import com.clouddisk.server.communication.request.LoginRequest;
import com.clouddisk.server.entity.User;

public class InformationCast {
    public static User loginRequestToUser(LoginRequest loginRequest){
        User user = new User();
        user.setUserName(loginRequest.getUserName());
        user.setPassword(loginRequest.getPassword());
        return user;
    }
}
