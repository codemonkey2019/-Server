package com.clouddisk.server.mapper;

import com.clouddisk.server.entity.User;

import java.util.List;

//@Mapper
public interface UserMapper {
    public List<User> selectByUserNameAndPassword(User user);

    public Integer registUserByNamePassword(User user);
}
