package com.clouddisk.server.config;

import com.cryptotool.block.DIG;
import com.cryptotool.digests.DigestFactory;
import com.cryptotool.digests.MyDigest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
@Slf4j
@Configuration
public class MyConfig {
    @Bean
    public MyDigest sm3Digist(){
        return DigestFactory.getDigest(DIG.SM3);
    }

    @Bean("userManagerExecutor")
    public Executor userManagerExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,100,1000L, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>(50), Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardPolicy());
        return threadPoolExecutor;
    }
    @Bean
    public Executor userServiceExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,100,1000L, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>(50), Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardPolicy());
        return threadPoolExecutor;
    }
}
