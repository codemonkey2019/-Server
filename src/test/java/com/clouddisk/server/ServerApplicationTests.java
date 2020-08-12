package com.clouddisk.server;

import com.clouddisk.server.entity.User;
import com.clouddisk.server.mapper.UserMapper;
import com.clouddisk.server.util.FileUtils;
import com.cryptotool.block.AE;
import com.cryptotool.cipher.MyCipher;
import com.cryptotool.cipher.MyCipherFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ServerApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        User user = new User(null,"lisi","123");
        System.out.println(userMapper.selectByUserNameAndPassword(user));
        System.out.println(userMapper.registUserByNamePassword(user));
    }

    @Test
    public void test() throws IOException {
//        AEKeyPair keyPair = KeyUtils.getAEKeyPair(AE.SM2);
//        FileUtils.writeFile("SM2KeyPair\\ec.pkcs8.pri.der",keyPair.getPrivateKey());
//        FileUtils.writeFile("SM2KeyPair\\ec.pkcs8.pri.der",keyPair.getPrivateKey());
        MyCipher cipher= MyCipherFactory.getAECipher(AE.SM2,
                FileUtils.readFile("SM2KeyPair\\ec.pkcs8.pri.der"),
                FileUtils.readFile("SM2KeyPair\\ec.x509.pub.der"));
        byte[] data = "hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello".getBytes();
        byte[] encrypt = cipher.encrypt(data);
        System.out.println(new String(cipher.decrypt(encrypt)));
    }
}
