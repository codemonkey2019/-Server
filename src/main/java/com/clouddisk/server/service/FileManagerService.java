package com.clouddisk.server.service;

import com.clouddisk.server.communication.request.SearchRequest;
import com.clouddisk.server.communication.request.UpdateRequest;
import com.clouddisk.server.communication.response.SearchAnswer;
import com.clouddisk.server.communication.response.UpdateAnswer;
import com.clouddisk.server.thread.ConnectedUser;

import java.net.Socket;
import java.util.List;

public interface FileManagerService {
    UpdateAnswer updateFile(UpdateRequest updateRequest, ConnectedUser connectedUser);

    SearchAnswer search(SearchRequest request, String userName);

    SearchAnswer searchAll(String userName);

    void sendFiles(List<String> fileNames, Socket socket,String userName);
}
