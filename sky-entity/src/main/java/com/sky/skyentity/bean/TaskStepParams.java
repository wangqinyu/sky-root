package com.sky.skyentity.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@ApiModel(value="TaskStep公共入参", description="TaskStep公共入参")
public class TaskStepParams {
    @ApiModelProperty(value = "指标Id")
    private Long stepId;
    @ApiModelProperty(value = "项目Id")
    private Long projectId;
    @ApiModelProperty(value = "指标序号")
    private Integer index;
    @ApiModelProperty(value = "指标名称")
    private String name;
    @ApiModelProperty(value = "指标别名")
    private String alias;
    @ApiModelProperty(value = "指标扩展")
    private String extra;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @ApiModelProperty(value = "分页和排序:对象类型（Map<String,String>-sort、Integer-size、Integer-page")
    private PageAndSortParams pageAndSortParams;

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
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

    public PageAndSortParams getPageAndSortParams() {
        return pageAndSortParams;
    }

    public void setPageAndSortParams(PageAndSortParams pageAndSortParams) {
        this.pageAndSortParams = pageAndSortParams;
    }
}
