package com.sky.skyentity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_task_info")
@DynamicInsert
@DynamicUpdate
public class TaskInfo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @Version//乐观锁
    private Long version;

    @Column(name = "task_pid")
    private Long taskPid;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_guid")
    private String userGuid;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "task_status")//任务状态：1.进行中 2.已提交 3.取消 9.完结
    private Integer status;

    @Column(name = "task_amount")
    private Long amount;

    @Column(name = "task_left")
    private Long left;

    @Column(name = "task_benefit")
    private Long benefit;

    @Column(name = "task_benefit_actual")
    private Long benefitActual;

    @Column(name = "task_report")
    private Long report;

    @Column(name = "task_confirm")
    private Long confirm;

    @Column(name = "createdAt",updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    @Column(name = "updatedAt",insertable = false,updatable = false,columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
