package com.clouddisk.server;

import com.clouddisk.server.entity.User;
import com.clouddisk.server.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        User user = new User(null,"lisi","123");
        System.out.println(userMapper.selectByUserNameAndPassword(user));
    }

}
