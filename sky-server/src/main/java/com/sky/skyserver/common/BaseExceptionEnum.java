package com.sky.skyserver.common;

public enum BaseExceptionEnum {

    USER_EXISTED("BE1000","USER_EXISTED","用户已存在"),
    USER_NOT_EXISTED("BE1001","USER_NOT_EXISTED","用户不存在"),
    USERNAME_EXISTED("BE1002","USERNAME_EXISTED","用户名已存在"),
    PHONE_EXISTED("BE1003","PHONE_EXISTED","手机号已存在"),
    MAIL_EXISTED("BE1004","MAIL_EXISTED","邮箱已存在"),
    PHONE_INCORRECT("BE1005","PHONE_INCORRECT","手机号码不正确"),
    USERNAME_INCORRECT("BE1006","USERNAME_INCORRECT","用户名不正确"),
    REALNAME_INCORRECT("BE1007","REALNAME_INCORRECT","用户姓名不正确"),
    CAPTCHA_INCORRECT("BE1008","CAPTCHA_INCORRECT","图形验证码不正确"),
    CAPTCHA_EXPIRED("BE1009","CAPTCHA_EXPIRED","图形验证码已过期"),
    SMS_CAPTCHA_INCORRECT("BE1010","SMS_CAPTCHA_INCORRECT","手机短信验证码不正确"),
    SMS_SEND_FAILED("BE1011","SMS_SEND_FAILED","手机短信验证码发送失败"),
    SMS_CAPTCHA_EXPIRED("BE1012","SMS_CAPTCHA_EXPIRED","短信验证码已过期"),
    PASSWORD_INCORRECT("BE1013","PASSWORD_INCORRECT","密码不正确"),
    MAIL_INCORRECT("BE1014","MAIL_INCORRECT","邮箱不正确"),
    SESSION_LOGIN_EXPIRED("BE1015","SESSION_LOGIN_EXPIRED","登录已过期"),
    LOGIN_TYPE_NOT_SUPPORT("BE1016","LOGIN_TYPE_NOT_SUPPORT","不支持的登录方式"),
    PROJECT_NAME_EXISTED("BE1017","PROJECT_NAME_EXISTED","项目名称已存在"),
    PROJECT_NOT_EXISTED("BE1018","PROJECT_NOT_EXISTED","项目不存在"),
    USER_TYPE_NOT_SUPPORT("BE1019","USER_TYPE_NOT_SUPPORT","不支持的用户类型"),
    TASK_NOT_EXISTED("BE1020","TASK_NOT_EXISTED","任务不存在"),
    CLAIM_TASK_SELF_NOT_SUPPORT("BE1021","CLAIM_TASK_SELF_NOT_SUPPORT","无法从自己创建的任务领取"),
    TASK_LEFT_NOT_ENOUGH("BE1022","TASK_LEFT_NOT_ENOUGH","可领取额度不足"),
    TASK_EXISTED("BE1021","TASK_EXISTED","任务已存在"),
    USER_STATUS_NOT_ONLINE("BE1022","USER_STATUS_NOT_ONLINE","用户未激活或禁用"),
    PROJECT_STATUS_NOT_ONLINE("BE1023","PROJECT_STATUS_NOT_ONLINE","项目未上线或已下架"),
    TASK_STATUS_NOT_ONLINE("BE1024","TASK_STATUS_NOT_ONLINE","任务已取消或完结"),
    URL_TYPE_NOT_SUPPORT("BE1025","URL_TYPE_NOT_SUPPORT","不支持的链接类型"),
    TASK_STEP_EXISTED("BE1026","TASK_STEP_EXISTED","任务标签已存在"),
    TASK_STEP_NOT_EXISTED("BE1027","TASK_STEP_NOT_EXISTED","任务标签不存在"),


    SYSTEM_ERROR("BE4000","SYSTEM_ERROR","系统错误"),
    INTERNET_ERROR("BE4001","INTERNET_ERROR","网络错误"),


    VALUE_NOT_COMPLETED("BE6000","VALUE_NOT_COMPLETED","请求数据不完整"),
    VALUE_ERROR("BE6001","VALUE_ERROR","请求数据异常"),
    USERNAME_IS_BLANK("BE6002","USERNAME_IS_BLANK","用户名为空"),
    PASSWORD_IS_BLANK("BE6003","PASSWORD_IS_BLANK","密码为空"),
    PHONE_IS_BLANK("BE6004","PHONE_IS_BLANK","手机号码为空"),
    PROJECT_NAME_IS_BLANK("BE6005","PROJECT_NAME_IS_BLANK","项目名为空"),
    PROJECT_AMOUNT_IS_BLANK("BE6006","PROJECT_AMOUNT_IS_BLANK","项目总数量为空"),
    LOG_MEMO_IS_BLANK("BE6007","LOG_MEMO_IS_BLANK","日志标记为空"),


    UNKNOWN_EXCEPTION("BE9000","UNKNOWN_EXCEPTION","未知异常");

    private String code;
    private String type;
    private String message;

    BaseExceptionEnum(String code, String type, String message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
