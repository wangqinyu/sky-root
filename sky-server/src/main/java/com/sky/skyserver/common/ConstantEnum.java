package com.sky.skyserver.common;

import static org.apache.commons.configuration.FileOptionsProvider.CURRENT_USER;

public enum ConstantEnum {
    //业务常量
    LOGIN_TYPE_USERNAME(1,"登录方式：用户名密码登录"),
    LOGIN_TYPE_PHONE(2,"登录方式：短信验证码登录"),
    PROJECT_STATUS_NOT_PUBLISH(1,"项目状态：未上线"),
    PROJECT_STATUS_ONLINE(2,"项目状态：已上线"),
    PROJECT_STATUS_OFFLINE(3,"项目状态：已下架"),
    USER_STATUS_NOT_ACTIVE(0,"用户状态：未激活"),
    USER_STATUS_ACTIVE(1,"用户状态：已激活"),
    USER_STATUS_DISABLE(2,"用户状态：禁用"),
    USER_AUTH_ADMIN(1,"用户权限：管理员"),
    USER_AUTH_NORMAL(2,"用户权限：普通用户"),
    TASK_STATUS_INIT(1,"任务状态：进行中"),
    TASK_STATUS_SUBMIT(2,"任务状态：已提交"),
    TASK_STATUS_CANCEL(3,"任务状态：取消"),
    TASK_STATUS_DONE(9,"任务状态：完结"),
    USER_TYPE_PLAT_FORM(1,"平台用户"),
    USER_TYPE_HEAD_OFFICE(2,"总公司用户"),
    USER_TYPE_BRANCH_OFFICE(4,"分公司用户"),
    USER_TYPE_EXTENSION_WORKER(8,"推广员用户"),
    LOG_MEMO_USER_LOGIN("用户登录","用户登录"),
    LOG_MEMO_USER_LOGOUT("用户登出","用户登出"),
    LOG_MEMO_USER_UPDATE("用户更新","用户更新"),
    LOG_MEMO_USER_DELETE("用户删除","用户删除"),
    LOG_MEMO_USER_REGISTER("用户注册","用户注册"),
    URL_TYPE_PUBLIC(1,"URL类型：公共URL"),
    URL_TYPE_SELF(2,"URL类型：私有URL"),

    //非业务常量
    SORT_ASC("ASC","排序策略——升序"),
    SORT_DESC("DESC","排序策略——降序"),
    REDIS_VALIDTIME_FIVE_MINUTE(5L,"Redis有效时间5分钟"),
    REDIS_VALIDTIME_TWENTY_MINUTE(20L,"Redis有效时间20分钟"),
    SESSION_CURRENT_USER(CURRENT_USER,"Session中用户信息标记"),
    PAGE_SIZE_TWENTY("20","分页尺寸20"),
    PAGE_NUM_FIRST("1","分页页码1"),
    SMS_SUCCESS("OK","请求成功"),
    QRCODE_SIZE_200(200,"200*200px"),
    COLOR_BLACK("0xFF000000","黑色"),
    COLOR_WHITE("0xFFFFFFFF","白色"),
    QRCODE_STYLE_RECT("RECT","矩形");
    private Object code;
    private String message;


    ConstantEnum(Object code, String message) {
        this.code = code;
        this.message = message;
    }

    public Object getCode() {
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

    public int getIntCode() {

        return (int) code;
    }

    public long getLongCode() {
        return (long) code;
    }

    public String getStringCode() {
        return (String) code;
    }
}
