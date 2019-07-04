package com.sky.skyserver.common;

import com.sky.skyserver.exception.BaseException;

import java.io.Serializable;

public class BaseDataResp<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String SUCCESS_CODE = "200";
    private String code;
    private String message;
    private T data;

    /*异常统一返回*/
    public BaseDataResp(Exception e) {
        if (e instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            this.code = baseException.getCode();
            this.message = baseException.getMessage();
        }else {
            this.code = BaseExceptionEnum.INTERNET_ERROR.getCode();
            this.message = BaseExceptionEnum.INTERNET_ERROR.getMessage();
        }
    }
    /*操作成功返回（无数据）*/
    public BaseDataResp(String message) {
        this.code = SUCCESS_CODE;
        this.message = message;
    }
    /*操作成功返回（有数据）*/
    public BaseDataResp(String message, T data) {
        this.code = SUCCESS_CODE;
        this.message = message;
        this.data = data;
    }
    /*自定义返回*/
    public BaseDataResp(String code, String message,T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
