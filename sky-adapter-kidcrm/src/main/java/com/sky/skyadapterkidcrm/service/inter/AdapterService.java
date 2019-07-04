package com.sky.skyadapterkidcrm.service.inter;

import com.sky.skyadapterkidcrm.bean.KidcrmResp;

public interface AdapterService {
    //查询用户数据
    KidcrmResp searchOne(String userGuid, String startTime, String endTime) throws Exception;
    //定时任务获取数据
    void fetchAllWithJob(Long projectId) throws Exception;
}
