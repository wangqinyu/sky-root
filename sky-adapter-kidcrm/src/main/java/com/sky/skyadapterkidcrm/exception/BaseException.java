package com.sky.skyadapterkidcrm.exception;


import com.sky.skyadapterkidcrm.common.BaseExceptionEnum;

public class BaseException extends Exception{
    private static final long serialVersionUID = 6958499248468627021L;
    private String code;
    private String type;
    private String message;

    public BaseException() {
        super();
    }

    /**
     * 自定义业务异常
     * @param exceptionEnum
     */
    public BaseException(BaseExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.type = exceptionEnum.getType();
        this.message = exceptionEnum.getMessage();
    }

    /**
     *
     * @param cause
     * @param exceptionEnum
     */
    public BaseException(Throwable cause, BaseExceptionEnum exceptionEnum) {
        super(cause);
        this.code = exceptionEnum.getCode();
        this.type = exceptionEnum.getType();
        this.message = exceptionEnum.getMessage();
    }

    public BaseException(Throwable cause, String code, String type, String message) {
        super(cause);
        this.code = code;
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
