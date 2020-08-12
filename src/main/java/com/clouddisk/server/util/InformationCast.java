package com.clouddisk.server.util;

import com.alibaba.fastjson.JSON;
import com.clouddisk.server.communication.MessageBody;
import com.clouddisk.server.communication.request.UserManagerRequest;
import com.clouddisk.server.config.ApplicationContextProvider;
import com.clouddisk.server.entity.User;
import com.cryptotool.cipher.MyCipher;
import com.cryptotool.util.MyStringUtils;

public class InformationCast {
    private static MyCipher serverSM2Cipher = (MyCipher)ApplicationContextProvider.getBean("serverSM2Cipher");
    public static <T> T messageBodyToRequestBody(MessageBody messageBody, Class<T> clazz){
        String body = messageBody.getMessageBodyJSON();
        if ("/login".equals(messageBody.getPath())||"/regist".equals(messageBody.getPath())){
            byte[] byteBody= MyStringUtils.decodeFromBase64tring(body);
            byteBody = serverSM2Cipher.decrypt(byteBody);
            return JSON.parseObject(byteBody, clazz);
        }
        return JSON.parseObject(body,clazz);
    }
    public static String objectToJson(Object obj){
        return JSON.toJSONString(obj);
    }

    public static User loginRequestToUser(UserManagerRequest loginRequest) {
        return new User(null,loginRequest.getUserName(),loginRequest.getPassword());
    }
}
