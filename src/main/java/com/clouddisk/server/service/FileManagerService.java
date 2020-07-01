package com.clouddisk.server.service;

import com.clouddisk.server.communication.request.UpdateRequest;
import com.clouddisk.server.communication.response.UpdateAnswer;

import java.net.Socket;

public interface FileManagerService {
    UpdateAnswer updateFile(UpdateRequest updateRequest, Socket socket);
}
