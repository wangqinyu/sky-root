package com.sky.skyadapterkidcrm.util;

import com.sky.skyadapterkidcrm.bean.KidcrmReq;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrmUtil {
    public static String getSign(KidcrmReq request) throws IOException {
        if (request.getRequestid() == null || request.getVersion() == null || request.getTime() == null
                || request.getAppcode() == null) {
            return null;
        }

        String sign = "requestid=" + request.getRequestid() + "&version=" + request.getVersion() + "&time="
                + request.getTime() + "&appcode=" + request.getAppcode() + "&key=kcrm2018";
        return getSHA256(sign);
    }

    public static String getSHA256(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
