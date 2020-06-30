package com.clouddisk.server.mapper;

import com.clouddisk.server.entity.User;

//@Mapper
public interface UserMapper {
    public int selectByUserNameAndPassword(User user);
}
