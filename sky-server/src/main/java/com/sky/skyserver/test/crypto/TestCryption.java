package com.sky.skyserver.test.crypto;

import com.sky.skyserver.util.security.CryptionUtils;
import com.sky.skyserver.util.security.PasswordKeys;

public class TestCryption {
    public static void main(String[] args) {
        String original = "123456789890!@#$%^&*(  )_+=dhksahdksahdkashdiuohoidhashjcbakdjiowhoiiqhwdikaslhudfhiahsnckjashdkashdkashkjdhasjkdhkjashjdkhaskjhdkash-QWERTYUIOP{},.<>asdfghjkl;'你好";
        PasswordKeys encryption = CryptionUtils.encryption(original);
        System.out.println("私钥："+encryption.getPrikey());
        System.out.println("密文："+encryption.getPasswordEncryption());
        String decryption = CryptionUtils.decryption(encryption.getPasswordEncryption(), encryption.getPrikey());
        System.out.println("原文："+decryption);

        if (original.equals(decryption)) System.out.println("相等");
    }
}
