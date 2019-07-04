package com.sky.skyentity.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value="TaskHistory公共入参", description="TaskHistory公共入参")
public class TaskHistoryParams {
    @ApiModelProperty(value = "任务历史Id")
    private Long historyId;
    @ApiModelProperty(value = "任务Id")
    private Long taskId;
    @ApiModelProperty(value = "用户Id")
    private Long userId;
    @ApiModelProperty(value = "项目Id")
    private Long projectId;
    @ApiModelProperty(value = "领取任务数量")
    private Long amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt_start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt_end;
    private Date updatedAt_start;
    private Date updatedAt_end;

    @ApiModelProperty(value = "分页和排序:对象类型（Map<String,String>-sort、Integer-size、Integer-page")
    private PageAndSortParams pageAndSortParams;

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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
