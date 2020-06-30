package com.clouddisk.server.service;

import com.clouddisk.server.communication.request.LoginRequest;

public interface UserManagerService {
    String login(LoginRequest loginRequest);
}
