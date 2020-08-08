package com.clouddisk.server;

import com.clouddisk.server.config.ApplicationContextProvider;
import com.clouddisk.server.config.MyConfig;
import com.clouddisk.server.thread.UserManagerCacheThread;
import com.clouddisk.server.thread.UserManagerDispatcherRun;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

@SpringBootApplication
@MapperScan("com.clouddisk.server.mapper")
@Slf4j
@Import(MyConfig.class)
public class ServerApplication implements CommandLineRunner {
    @Autowired
    private UserManagerCacheThread cacheThread;
    @Autowired
    private Executor userManagerExecutor;
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8888);
                log.info("服务器启动，端口号8888");
                cacheThread.start();
                while (true) {
                    log.info("等待连接");
                    final Socket socket = serverSocket.accept();//建立与服务器的连接
                    String user = socket.getInetAddress().getHostName();
                    log.info("用户" + user + "建立连接");
                    //将登录任务放入执行器
                    UserManagerDispatcherRun run = ApplicationContextProvider.getBean(UserManagerDispatcherRun.class);
                    run.setSocket(socket);
                    userManagerExecutor.execute(run);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
