package com.clouddisk.server.service;

import com.clouddisk.server.communication.request.SearchRequest;
import com.clouddisk.server.communication.request.UpdateRequest;
import com.clouddisk.server.communication.response.SearchAnswer;
import com.clouddisk.server.communication.response.UpdateAnswer;

import java.net.Socket;
import java.util.List;

public interface FileManagerService {
    UpdateAnswer updateFile(UpdateRequest updateRequest, Socket socket);

    SearchAnswer search(SearchRequest request);

    SearchAnswer searchAll();

    void sendFiles(List<String> fileNames, Socket socket);
}
