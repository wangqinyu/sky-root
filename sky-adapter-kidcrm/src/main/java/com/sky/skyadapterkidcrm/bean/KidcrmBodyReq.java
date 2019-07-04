package com.sky.skyadapterkidcrm.bean;

import org.springframework.format.annotation.DateTimeFormat;

public class KidcrmBodyReq {
    private String userid;
    private String informationChannel;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

    public KidcrmBodyReq(String userid , String startTime, String endTime) {
        this.userid = userid;
        this.startTime = startTime;
        this.endTime = endTime;
        this.informationChannel = "XXLY02401";
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getInformationChannel() {
        return informationChannel;
    }

    public void setInformationChannel(String informationChannel) {
        this.informationChannel = informationChannel;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
