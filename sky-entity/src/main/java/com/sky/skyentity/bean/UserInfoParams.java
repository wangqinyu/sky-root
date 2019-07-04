package com.sky.skyentity.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@ApiModel(value="UserInfo公共入参", description="UserInfo公共入参")
public class UserInfoParams {
    @ApiModelProperty(value = "用户Id")
    private Long userId;
    @ApiModelProperty(value = "用户Id，对外使用")
    private String userGuid;
    @ApiModelProperty(value = "上级用户ID")
    private Long userPid;
    @ApiModelProperty(value = "用户状态：0 未激活 1 已激活 2 禁用")
    private Integer status;
    @ApiModelProperty(value = "用户权限")
    private Long userAuth;
    @ApiModelProperty(value = "用户类型：1平台 ，2总公司 4分公司 8 推广员")
    private Integer type;
    @ApiModelProperty(value = "用户名（账户名）")
    private String name;
    @ApiModelProperty(value = "密码")
    private String password;
    private String salt;
    @ApiModelProperty(value = "用户名称")
    private String realName;
    @ApiModelProperty(value = "手机号码")
    private String phone;
    @ApiModelProperty(value = "邮箱")
    private String mail;
    @ApiModelProperty(value = "用户信誉（分数）")
    private Long score;
    @ApiModelProperty(value = "用户信誉最小值（查询条件）")
    private Long score_start;
    @ApiModelProperty(value = "用户信誉最大值（查询条件）")
    private Long score_end;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt_start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt_end;
    private Date updatedAt_start;
    private Date updatedAt_end;

    @ApiModelProperty(value = "短信验证码Id")
    private String smsCaptchaId;
    @ApiModelProperty(value = "短信验证码")
    private String smsCaptcha;
    @ApiModelProperty(value = "图形验证码Id")
    private String picCaptchaId;
    @ApiModelProperty(value = "图形验证码")
    private String picCaptcha;
    @ApiModelProperty(value = "登录类型")
    private Integer loginType;
    @ApiModelProperty(value = "分页和排序:对象类型（Map<String,String>-sort、Integer-size、Integer-page")
    private PageAndSortParams pageAndSortParams;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public Long getUserPid() {
        return userPid;
    }

    public void setUserPid(Long userPid) {
        this.userPid = userPid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(Long userAuth) {
        this.userAuth = userAuth;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getScore_start() {
        return score_start;
    }

    public void setScore_start(Long score_start) {
        this.score_start = score_start;
    }

    public Long getScore_end() {
        return score_end;
    }

    public void setScore_end(Long score_end) {
        this.score_end = score_end;
    }

    public Date getCreatedAt_start() {
        return createdAt_start;
    }

    public void setCreatedAt_start(Date createdAt_start) {
        this.createdAt_start = createdAt_start;
    }

    public Date getCreatedAt_end() {
        return createdAt_end;
    }

    public void setCreatedAt_end(Date createdAt_end) {
        this.createdAt_end = createdAt_end;
    }

    public Date getUpdatedAt_start() {
        return updatedAt_start;
    }

    public void setUpdatedAt_start(Date updatedAt_start) {
        this.updatedAt_start = updatedAt_start;
    }

    public Date getUpdatedAt_end() {
        return updatedAt_end;
    }

    public void setUpdatedAt_end(Date updatedAt_end) {
        this.updatedAt_end = updatedAt_end;
    }

    public String getSmsCaptchaId() {
        return smsCaptchaId;
    }

    public void setSmsCaptchaId(String smsCaptchaId) {
        this.smsCaptchaId = smsCaptchaId;
    }

    public String getSmsCaptcha() {
        return smsCaptcha;
    }

    public void setSmsCaptcha(String smsCaptcha) {
        this.smsCaptcha = smsCaptcha;
    }

    public String getPicCaptchaId() {
        return picCaptchaId;
    }

    public void setPicCaptchaId(String picCaptchaId) {
        this.picCaptchaId = picCaptchaId;
    }

    public String getPicCaptcha() {
        return picCaptcha;
    }

    public void setPicCaptcha(String picCaptcha) {
        this.picCaptcha = picCaptcha;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public PageAndSortParams getPageAndSortParams() {
        return pageAndSortParams;
    }

    public void setPageAndSortParams(PageAndSortParams pageAndSortParams) {
        this.pageAndSortParams = pageAndSortParams;
    }
}
