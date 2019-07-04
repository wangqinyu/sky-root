package com.sky.skyadapterkidcrm.service.inter;

import com.sky.skyentity.bean.TaskResultParams;
import com.sky.skyentity.entity.TaskResult;
import com.sky.skyadapterkidcrm.exception.BaseException;

import java.util.List;

public interface TaskResultService {
    //条件查询
    List<TaskResult> findByParams(TaskResultParams taskResultParams);
    //存储任务结果
    void save(TaskResult taskResult) throws BaseException;
}
