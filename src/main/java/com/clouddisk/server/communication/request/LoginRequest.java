package com.clouddisk.server.communication.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements UserManagerRequest, Serializable {
    private String userName;
    private String password;
}
