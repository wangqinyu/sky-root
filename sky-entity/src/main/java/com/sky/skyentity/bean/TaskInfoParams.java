package com.sky.skyentity.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@ApiModel(value="TaskInfo公共入参", description="TaskInfo公共入参")
public class TaskInfoParams {
    @ApiModelProperty(value = "任务Id")
    private Long taskId;
    @ApiModelProperty(value = "上级任务Id")
    private Long taskPid;
    @ApiModelProperty(value = "用户Id")
    private Long userId;
    @ApiModelProperty(value = "用户Id，对外使用")
    private String userGuid;
    @ApiModelProperty(value = "项目Id")
    private Long projectId;
    @ApiModelProperty(value = "任务状态：1.进行中 2.已提交 3.取消 9.完结")
    private Integer status;
    @ApiModelProperty(value = "任务总数量")
    private Long amount;
    @ApiModelProperty(value = "当前领取数量")
    private Long claimAmount;
    @ApiModelProperty(value = "任务剩余量")
    private Long left;
    @ApiModelProperty(value = "任务收益")
    private Long benefit;
    @ApiModelProperty(value = "任务实际收益")
    private Long benefitActual;
    @ApiModelProperty(value = "任务上报数量")
    private Long report;
    @ApiModelProperty(value = "任务确认数量")
    private Long confirm;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt_start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt_end;
    private Date updatedAt_start;
    private Date updatedAt_end;

    @ApiModelProperty(value = "分页和排序:对象类型（Map<String,String>-sort、Integer-size、Integer-page")
    private PageAndSortParams pageAndSortParams;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskPid() {
        return taskPid;
    }

    public void setTaskPid(Long taskPid) {
        this.taskPid = taskPid;
    }

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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(Long claimAmount) {
        this.claimAmount = claimAmount;
    }

    public Long getLeft() {
        return left;
    }

    public void setLeft(Long left) {
        this.left = left;
    }

    public Long getBenefit() {
        return benefit;
    }

    public void setBenefit(Long benefit) {
        this.benefit = benefit;
    }

    public Long getBenefitActual() {
        return benefitActual;
    }

    public void setBenefitActual(Long benefitActual) {
        this.benefitActual = benefitActual;
    }

    public Long getReport() {
        return report;
    }

    public void setReport(Long report) {
        this.report = report;
    }

    public Long getConfirm() {
        return confirm;
    }

    public void setConfirm(Long confirm) {
        this.confirm = confirm;
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
