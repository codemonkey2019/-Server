package com.clouddisk.server.dao.impl;

import com.clouddisk.server.dao.UserDao;
import com.clouddisk.server.entity.User;
import com.clouddisk.server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")@Scope("prototype")
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserMapper mapper;
    @Override
    public boolean login(User user) {
        boolean out = false;
        List<User> num = mapper.selectByUserNameAndPassword(user);
        if (num!=null &&num.size()==1){
            out=true;
        }
        return out;
    }

    @Override
    public boolean regist(User user) {
        boolean success = false;
        Integer num = mapper.registUserByNamePassword(user);
        if (num!=null &&num==1){
            success=true;
        }
        return success;
    }
}
