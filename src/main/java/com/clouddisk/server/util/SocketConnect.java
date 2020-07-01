package com.clouddisk.server.util;

import com.alibaba.fastjson.JSON;
import com.clouddisk.server.communication.MessageBody;

import java.io.*;
import java.net.Socket;

public class SocketConnect {
    public static void sendAnwswerToClient(Socket socket, Object answer) {
        String message = InformationCast.objectToJson(answer);
        MessageBody body = new MessageBody(null, message);
        BufferedWriter bw;
        if (!socket.isClosed()) {
            try {
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bw.write(JSON.toJSONString(body));
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static MessageBody getMessageBodyFromClient(Socket socket) {
        BufferedReader br;
        MessageBody messageBody = new MessageBody("null",null);
        if (!socket.isClosed()) {
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String data = br.readLine();
                messageBody = JSON.parseObject(data, MessageBody.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return messageBody;
    }
}
