package com.clouddisk.server.config;

import com.cryptotool.block.DIG;
import com.cryptotool.digests.DigestFactory;
import com.cryptotool.digests.MyDigest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    @Bean
    public MyDigest sm3Digist(){
        return DigestFactory.getDigest(DIG.SM3);
    }
}
