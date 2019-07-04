package com.sky.skyserver.service.inter;

import com.sky.skyentity.bean.TaskResultParams;
import com.sky.skyentity.entity.TaskResult;

import java.util.List;

public interface TaskResultService {
    //条件查询
    List<TaskResult> findByParams(TaskResultParams taskResultParams);
    //可选条件查询
    List<TaskResult> findByOptional(TaskResultParams taskResultParams);
    //统计
    Long count();
    //条件统计
    Long countByParams(TaskResultParams taskResultParams);
}
