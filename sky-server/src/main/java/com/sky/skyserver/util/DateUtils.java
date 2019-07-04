package com.sky.skyserver.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    //获取当前时间字符串（格式为：yyyy-MM-dd HH:mm:ss）
    public static String currentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());
        return currentTime;
    }
    //获取当前时间字符串（格式为：yyyy-MM-dd）
    public static String currentDay(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = simpleDateFormat.format(new Date());
        return currentTime;
    }
    //获取当前时间字符串（格式为：yyyyMMdd）
    public static String currentDayWithOutLine(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String currentTime = simpleDateFormat.format(new Date());
        return currentTime;
    }
    //时间格式转换字符串（格式为：yyyy-MM-dd HH:mm:ss）
    public static String parseTime(Date time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(time);
        return currentTime;
    }
    //获取前一天时间（格式为：yyyy-MM-dd HH:mm:ss）
    public static String getYesterday(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal= Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date date = cal.getTime();
        String currentTime = simpleDateFormat.format(date);
        return currentTime;
    }
}
