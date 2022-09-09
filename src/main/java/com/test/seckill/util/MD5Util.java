package com.test.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    private static final String s = "1a2b3c4d";

    public static String md5(String str) {
        return DigestUtils.md5Hex(str + s);
    }

    public static String md5(String str, String salt) {
        return DigestUtils.md5Hex(str + salt);
    }

    public static void main(String[] args) {
        String s = md5("123");
        System.out.println("加密后的密码：" + s);
    }
}
