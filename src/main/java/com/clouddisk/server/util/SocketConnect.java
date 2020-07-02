package com.clouddisk.server.util;

import com.alibaba.fastjson.JSON;
import com.clouddisk.server.communication.MessageBody;

import java.io.*;
import java.net.Socket;

public class SocketConnect {
    public static void sendFileToClient(Socket socket, String filePath){
        File sendFile = new File(filePath);
        if (sendFile.exists()) {
            DataOutputStream bos=null;
            BufferedInputStream bis=null;
            try {
                bos = new DataOutputStream(socket.getOutputStream());
                bis = new BufferedInputStream(new FileInputStream(sendFile));
                //发送文件长度
                long length = sendFile.length();
                bos.writeLong(length);
                bos.flush();
                System.out.println(filePath);
                System.out.println(sendFile.length());
                byte[] b = new byte[1024];
                int len = 0;
                while ((len=bis.read(b))!=-1){
                    bos.write(b,0,len);
                    bos.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (bis!=null){
                    try {
                        bis.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
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
    public static boolean receiveFile(String path, Socket socket){
        File file = new File(path);
        BufferedOutputStream bos=null;
        BufferedInputStream bis = null;
        DataInputStream dis =null;
        try {
            bis=new BufferedInputStream(socket.getInputStream());
            dis = new DataInputStream(bis);
            bos = new BufferedOutputStream(new FileOutputStream(file));

            long length = dis.readLong();
            byte[] b = new byte[1024];
            int len = 0;
            long sum = 0L;
            while ((sum+=(len=bis.read(b)))!=length){
                bos.write(b,0,len);
            }
            bos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (bos!=null){
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
        }

    }
}
