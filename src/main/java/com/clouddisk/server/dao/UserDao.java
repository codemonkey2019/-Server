package com.clouddisk.server.dao;

import com.clouddisk.server.entity.User;

public interface UserDao {
    boolean login(User user);

    boolean regist(User user);
}
