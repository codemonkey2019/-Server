package com.clouddisk.server.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.Socket;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConnectedUser {
    private String userName;
    private String password;
    private Socket socket;
}
