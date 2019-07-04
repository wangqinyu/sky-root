package com.sky.skyserver.service.inter;

import com.sky.skyentity.bean.TaskInfoParams;
import com.sky.skyentity.entity.TaskInfo;
import com.sky.skyserver.exception.BaseException;

import java.util.List;

public interface TaskInfoService {
    // 新增
    void save(TaskInfo taskInfo) throws BaseException;
    //查询全部任务
    List<TaskInfo> findAll();
    //按条件查询
    List<TaskInfo> findByParams(TaskInfoParams taskInfoParams);
    //领取任务
    void claimTask(TaskInfoParams taskInfoParams) throws BaseException;
    //获取任务信息
    TaskInfo getTaskInfo(Long taskId) throws BaseException;
    //更改任务状态
    void updateStatus(TaskInfoParams taskInfoParams) throws BaseException;
}
