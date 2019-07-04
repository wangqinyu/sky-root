package com.sky.skyadapterkidcrm.bean;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;

import static com.sky.skyadapterkidcrm.util.DateUtils.currentTime;

public class KidcrmReq {
    private String requestid;
    private String version;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String time;
    private String appcode;
    private String sign;
    private KidcrmBodyReq body;

    public KidcrmReq(KidcrmBodyReq body) {
        this.body = body;
        this.requestid = UUID.randomUUID().toString().replaceAll("-","");
        this.version = "1.0";
        this.appcode = "kcrm2018";
        this.time = currentTime();
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public KidcrmBodyReq getBody() {
        return body;
    }

    public void setBody(KidcrmBodyReq body) {
        this.body = body;
    }
}
