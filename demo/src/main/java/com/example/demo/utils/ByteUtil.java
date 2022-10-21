package com.example.demo.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/6/7 14:00
 * @Version: 1.0.0
 * @Description: byte 与 16进行互转的工具类
 */
public class ByteUtil {

    public static  String byteHex(byte[] bytes){
        StringBuilder hex = new StringBuilder();
        for (byte b:bytes
        ) {
            String s = Integer.toHexString(b & 0xff);
            hex.append(Integer.toHexString(b & 0xff));
        }
        return hex.toString();
    }

    /**
     * 将16进制字符串转化为byte字节码
     * @param hex
     * @return
     */
    public static byte[] hexbyte(String hex){
        if(hex.length() % 2 != 0){
            throw new RuntimeException("不是由byte转来的16进制字符串");
        }
        byte[] bytes = new byte[hex.length()/2];
        char[] chars = hex.toCharArray();
        for (int i = 0, j  =0; i < chars.length; i += 2,j ++) {
            int var1 = Integer.parseInt(String.valueOf(chars[i]), 16);
            int var2 = Integer.parseInt(String.valueOf(chars[i+1]), 16);
            bytes[j] = (byte) (var1*16 + var2);
        }
        return bytes;
    }
}
