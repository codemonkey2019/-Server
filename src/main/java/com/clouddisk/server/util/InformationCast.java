package com.clouddisk.server.util;

import com.alibaba.fastjson.JSON;
import com.clouddisk.server.communication.MessageBody;
import com.clouddisk.server.communication.request.UserManagerRequest;
import com.clouddisk.server.entity.User;

public class InformationCast {

    public static <T> T messageBodyToRequestBody(MessageBody messageBody, Class<T> clazz){
        return JSON.parseObject(messageBody.getMessageBodyJSON(), clazz);
    }
    public static String objectToJson(Object obj){
        return JSON.toJSONString(obj);
    }

    public static User loginRequestToUser(UserManagerRequest loginRequest) {
        return new User(null,loginRequest.getUserName(),loginRequest.getPassword());
    }
}
