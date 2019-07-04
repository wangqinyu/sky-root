package com.sky.skyentity.bean;

import java.util.Map;

public class PageAndSortParams {
    //排序属性<String>、排序方向<正序：ASC  倒序：DESC>
    private Map<String,String> sort;
    //分页尺寸
    private Integer size;
    //页码
    private Integer page;

    public Map<String, String> getSort() {
        return sort;
    }

    public void setSort(Map<String, String> sort) {
        this.sort = sort;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
