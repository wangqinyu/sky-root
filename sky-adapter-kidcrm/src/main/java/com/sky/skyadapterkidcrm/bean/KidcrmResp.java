package com.sky.skyadapterkidcrm.bean;

import java.util.UUID;

import static com.sky.skyadapterkidcrm.util.DateUtils.currentTime;

public class KidcrmResp {
    private String responseid;
    private String code;
    private String msg;
    private KidcrmBodyResp body;

    public String getResponseid() {
        return responseid;
    }

    public void setResponseid(String responseid) {
        this.responseid = responseid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public KidcrmBodyResp getBody() {
        return body;
    }

    public void setBody(KidcrmBodyResp body) {
        this.body = body;
    }
}
