package com.clouddisk.server.util;

import com.cryptotool.block.DIG;
import com.cryptotool.digests.DigestFactory;
import com.cryptotool.digests.MyDigest;

public class CryptoUtils {
    private static final MyDigest DIGEST = DigestFactory.getDigest(DIG.SM3);
    public static String hashToString(String data){
        return DIGEST.getDigest(data);
    }
    public static String pseoToString(String key,String data){
        return DIGEST.getDigest(key+data);
    }
}
