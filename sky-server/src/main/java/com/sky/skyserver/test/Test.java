package com.sky.skyserver.test;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


public class Test {
    @Resource
    RedisTemplate redisTemplate;
    //base64
    /*public static void main(String[] args) {
     *//* //设置返回数据
        Captcha captcha = new GifCaptcha();
        //captcha.out(response.getOutputStream());
        try {
            captcha.out(new FileOutputStream(new File("C:/Users/Administrator/Desktop/test.gif")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*//*

        Captcha captcha = new GifCaptcha();
        System.out.println("验证码："+captcha.text());
        String gifByBase64 = null;
        try {
            gifByBase64 = captcha.getPicByBase64();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("==========编码=================");
        System.out.println(gifByBase64);
        System.out.println("===========================");
        byte[] decode = Base64.getDecoder().decode(gifByBase64);
        //将字节写入文件
        try {
            FileOutputStream fout = new FileOutputStream(new File("C:/Users/Administrator/Desktop/test.gif"));
            fout.write(decode);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    //redis
    /*public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 6 ; i++) {
            stringBuffer.append(String.valueOf((int)Math.floor(Math.random()*10)));
        }
        System.out.println(stringBuffer.toString());
    }*/

    /*public static void main(String[] args) {
        String a = "O";
        String b = "0";
        System.out.println("a=" + a);
        System.out.println("b=" + b);
        if (a.equals(b)) System.out.println("相等");

    }*/

/*    public static void main(String[] args) {
        String msg = "http://tool.chinaz.com/tools/imgtobase/";
        String bufferedImage = null;
        try {
            bufferedImage = QrCodeGenWrapper.of(msg).setW(500).setH(500).setDrawPreColor(Color.BLUE).setDrawBgColor(Color.GREEN).asString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        System.out.println(bufferedImage);
    }*/

    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal= Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date date = cal.getTime();
        String currentTime = simpleDateFormat.format(date);
        System.out.println(currentTime);
    }
}
