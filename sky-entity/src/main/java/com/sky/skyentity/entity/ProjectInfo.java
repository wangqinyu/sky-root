package com.sky.skyentity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_project_info")
@DynamicInsert
@DynamicUpdate
public class ProjectInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Version//乐观锁
    private Long version;

    @Column(name = "project_status")//项目状态：1 未上线 2 已上线 3 已下架
    private Integer status;

    @Column(name = "project_name")
    private String name;

    @Column(name = "project_desc")
    private String desc;

    @Column(name = "content")
    private String content;

    @Column(name = "amount")//总数量
    private Long amount;

    @Column(name = "left_amount")//剩余数量
    private Long left;

    @Column(name = "min_amount")//领取最小数量
    private Long minAmount;

    @Column(name = "max_amount")//领取最大数量
    private Long maxAmount;

    @Column(name = "start_time")//项目开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @Column(name = "end_time")//项目结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @Column(name = "url_type")//URL类型：1公共URL 2私有URL
    private Integer urlType;

    @Column(name = "url_gen")//url生成器
    private String urlGen;

    @Column(name = "post_flag")//上报标记：1上报 2不用上报
    private Integer postFlag;

    @Column(name = "createdAt",updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    @Column(name = "updatedAt",insertable = false,updatable = false,columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;

    //关联任务中的projectId外键
    @Transient //不查询子表
    @OneToMany(targetEntity = TaskInfo.class,mappedBy = "projectId",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<TaskInfo> taskInfoList = new HashSet<>();

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Long minAmount) {
        this.minAmount = minAmount;
    }

    public Long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getUrlType() {
        return urlType;
    }

    public void setUrlType(Integer urlType) {
        this.urlType = urlType;
    }

    public String getUrlGen() {
        return urlGen;
    }

    public void setUrlGen(String urlGen) {
        this.urlGen = urlGen;
    }

    public Integer getPostFlag() {
        return postFlag;
    }

    public void setPostFlag(Integer postFlag) {
        this.postFlag = postFlag;
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

    public Set<TaskInfo> getTaskInfoList() {
        return taskInfoList;
    }

    public void setTaskInfoList(Set<TaskInfo> taskInfoList) {
        this.taskInfoList = taskInfoList;
    }
}
