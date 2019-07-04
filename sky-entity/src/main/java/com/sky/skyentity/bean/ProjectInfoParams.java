package com.sky.skyentity.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@ApiModel(value="ProjectInfo公共入参", description="ProjectInfo公共入参")
public class ProjectInfoParams {
    @ApiModelProperty(value = "项目Id")
    private Long projectId;
    @ApiModelProperty(value = "项目状态：1 未上线 2 已上线 3 已下架")
    private Integer status;
    @ApiModelProperty(value = "项目名称")
    private String name;
    @ApiModelProperty(value = "项目描述")
    private String desc;
    @ApiModelProperty(value = "项目内容")
    private String content;
    @ApiModelProperty(value = "项目总数量")
    private Long amount;
    @ApiModelProperty(value = "项目剩余量")
    private Long left;
    @ApiModelProperty(value = "项目剩余量最小值（查询条件）")
    private Long left_start;
    @ApiModelProperty(value = "项目剩余量最大值（查询条件）")
    private Long left_end;
    @ApiModelProperty(value = "项目总数量最小值（查询条件）")
    private Long minAmount;
    @ApiModelProperty(value = "项目总数量最大值（查询条件）")
    private Long maxAmount;
    @ApiModelProperty(value = "项目起始时间最小值（查询条件）格式为：yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime_start;
    @ApiModelProperty(value = "项目起始时间最大值（查询条件）格式为：yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime_end;
    @ApiModelProperty(value = "项目截止时间最小值（查询条件）格式为：yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime_start;
    @ApiModelProperty(value = "项目截止时间最大值（查询条件）格式为：yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime_end;
    @ApiModelProperty(value = "项目URL类型（1公共URL 2私有URL）")
    private Integer urlType;
    @ApiModelProperty(value = "URL生成器")
    private String urlGen;
    @ApiModelProperty(value = "上传标记")
    private Integer postFlag;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt_start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt_end;
    private Date updatedAt_start;
    private Date updatedAt_end;


    @ApiModelProperty(value = "分页和排序:对象类型（Map<String,String>-sort、Integer-size、Integer-page")
    private PageAndSortParams pageAndSortParams;

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

    public Long getLeft_start() {
        return left_start;
    }

    public void setLeft_start(Long left_start) {
        this.left_start = left_start;
    }

    public Long getLeft_end() {
        return left_end;
    }

    public void setLeft_end(Long left_end) {
        this.left_end = left_end;
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

    public Date getStartTime_start() {
        return startTime_start;
    }

    public void setStartTime_start(Date startTime_start) {
        this.startTime_start = startTime_start;
    }

    public Date getStartTime_end() {
        return startTime_end;
    }

    public void setStartTime_end(Date startTime_end) {
        this.startTime_end = startTime_end;
    }

    public Date getEndTime_start() {
        return endTime_start;
    }

    public void setEndTime_start(Date endTime_start) {
        this.endTime_start = endTime_start;
    }

    public Date getEndTime_end() {
        return endTime_end;
    }

    public void setEndTime_end(Date endTime_end) {
        this.endTime_end = endTime_end;
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
