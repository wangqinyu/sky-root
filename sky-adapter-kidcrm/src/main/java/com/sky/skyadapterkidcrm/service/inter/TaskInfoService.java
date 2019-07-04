package com.sky.skyadapterkidcrm.service.inter;

import com.sky.skyentity.bean.TaskInfoParams;
import com.sky.skyentity.entity.TaskInfo;
import com.sky.skyadapterkidcrm.exception.BaseException;

import java.util.List;

public interface TaskInfoService {
    //按条件查询
    List<TaskInfo> findByParams(TaskInfoParams taskInfoParams);
    //获取任务信息
    TaskInfo getTaskInfo(Long taskId) throws BaseException;

}
