package com.clouddisk.server.service;

import com.clouddisk.server.communication.request.LoginRequest;
import com.clouddisk.server.communication.request.RegistRequest;
import com.clouddisk.server.communication.response.LoginAnswer;
import com.clouddisk.server.communication.response.RegistAnswer;

public interface UserManagerService {
    LoginAnswer login(LoginRequest loginRequest);

    RegistAnswer regist(RegistRequest request);
}
