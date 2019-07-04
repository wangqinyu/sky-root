package com.sky.skyentity.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value="UserLog公共入参", description="UserLog公共入参")
public class UserLogParams {
    @ApiModelProperty(value = "用户日志Id")
    private Long logId;
    @ApiModelProperty(value = "详细日志Id")
    private Long relayId;
    @ApiModelProperty(value = "用户Id")
    private Long userId;
    @ApiModelProperty(value = "日志标记")
    private String memo;
    @ApiModelProperty(value = "日志备注")
    private String extra;
    @ApiModelProperty(value = "IP地址")
    private String ip;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt_start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt_end;
    private Date updatedAt_start;
    private Date updatedAt_end;

    @ApiModelProperty(value = "分页和排序:对象类型（Map<String,String>-sort、Integer-size、Integer-page")
    private PageAndSortParams pageAndSortParams;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getRelayId() {
        return relayId;
    }

    public void setRelayId(Long relayId) {
        this.relayId = relayId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public PageAndSortParams getPageAndSortParams() {
        return pageAndSortParams;
    }

    public void setPageAndSortParams(PageAndSortParams pageAndSortParams) {
        this.pageAndSortParams = pageAndSortParams;
    }
}
