package com.sky.skyadapterkidcrm.service.inter;

import java.util.Map;

public interface JobService {
    //新增定时任务
    void addJob(String jobClassName, String jobGroupName, String cronExpression, String jobDescription,
                Map<String, Object> params) throws Exception;
    //删除定时任务
    void removeJob(String jobClassName, String jobGroupName) throws Exception;
    //暂停定时任务
    void pauseJob(String jobClassName, String jobGroupName) throws Exception;
    //恢复定时任务
    void resumejob(String jobClassName, String jobGroupName) throws Exception;
}
