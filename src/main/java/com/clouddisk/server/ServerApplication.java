package com.clouddisk.server;

import com.clouddisk.server.config.ApplicationContextProvider;
import com.clouddisk.server.config.MyConfig;
import com.clouddisk.server.efficientsearch.CacheManager;
import com.clouddisk.server.efficientsearch.KFNodeCache;
import com.clouddisk.server.thread.UserManagerCacheThread;
import com.clouddisk.server.thread.UserManagerDispatcherThread;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
@MapperScan("com.clouddisk.server.mapper")
@Slf4j
@Import(MyConfig.class)
public class ServerApplication implements CommandLineRunner {
    @Value("${cacheFolder}")
    private String cacheFolder;
    @Value("${fileFolder}")
    private String fileFolder;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private UserManagerCacheThread cacheThread;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        File folder1 = new File(cacheFolder);
        File folder2 = new File(fileFolder);
        if (!folder1.exists()||!folder1.isDirectory()){
            folder1.mkdir();
        }
        if (!folder2.exists()||!folder2.isDirectory()){
            folder2.mkdir();
        }
        File file = new File(KFNodeCache.CACHE_PATH);
        if (file.exists()) {
            //加载缓存文件
            cacheManager.loadCache();
        }

        new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8888);
                log.info("服务器启动，端口号8888");
                cacheThread.start();
                while (true) {
                    log.info("等待连接");
                    Socket socket = serverSocket.accept();//建立与服务器的连接
                    String user = socket.getInetAddress().getHostName();
                    String ip = socket.getInetAddress().getHostAddress();
                    log.info("用户" + user + "建立连接");
                    //为每一个请求开一个处理登录注册的线程
                    UserManagerDispatcherThread userManagerDispatcherThread = ApplicationContextProvider.getBean(UserManagerDispatcherThread.class);
                    userManagerDispatcherThread.getUserManagerDispatcherRun().setSocket(socket);
                    userManagerDispatcherThread.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
