package com.clouddisk.server.dao.impl;

import com.clouddisk.server.dao.UserDao;
import com.clouddisk.server.entity.User;
import com.clouddisk.server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserMapper mapper;
    @Override
    public boolean login(User user) {
        boolean out = false;
        int num = mapper.selectByUserNameAndPassword(user);
        if (num==1){
            out=true;
        }
        return out;
    }
}
