package com.sky.skyserver.test.crypto;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.math.ec.ECPoint;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class TestSM2 {

    public static void main(String[] args) {
        SM2 x = new SM2();
        SM2KeyPair keys = x.generateKeyPair();
        ECPoint pubKey = keys.getPublicKey();
        BigInteger privKey = keys.getPrivateKey();
        String p = "\"Hel12312lo+_=-!ihadhnjkasncaiuhcuncjkbahaduashdkjasncjkabcjabkHUHKJBBYGKJNKJBHGYIGUHUKBVJKBKBIUHSUIHNSKJBC:[]{}哈哈电脑萨科技 World";
        //加密
        byte[] data = x.encrypt(p, pubKey);
        System.out.println("加密密码的长度: " + data.length);
        //byte-> String
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b :data) {
            stringBuffer.append(Byte.toString(b)+",");
        }
        String a = stringBuffer.substring(0,stringBuffer.length()-1);
        System.out.println("加密密码："+a);
        System.out.println("加密密码长度："+a.length());
        //String->byte`
        String[] strs = a.split(",");
        byte[] password  = new byte[strs.length];
        for (int i =0;i<strs.length;i++) {
            password[i] = Byte.parseByte(strs[i]);
        }
        //解密
        String origin = x.decrypt(password, privKey);
        System.out.println("私钥:"+privKey);
        System.out.println("原密码: " + origin);
        if (origin.equals(p)){ System.out.println("原密码 相等");}
        else{ System.out.println("原密码 不相等");}
    }//encrypt: [B@21588809
}
