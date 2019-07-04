package com.sky.skyserver.service.inter;

import com.sky.skyentity.bean.TaskStepParams;
import com.sky.skyentity.entity.TaskStep;
import com.sky.skyserver.exception.BaseException;

import java.util.List;

public interface TaskStepService {
    //新增
    void save(TaskStep taskStep) throws BaseException;
    //查询全部
    List<TaskStep> findAll();
    //条件查询
    List<TaskStep> findByParams(TaskStepParams taskStepParams);
    //更新
    void update(TaskStep taskStep) throws BaseException;
    //可选条件查询
    List<TaskStep> findByOptional(TaskStepParams taskStepParams);
    //删除
    void delete(Long taskId) throws BaseException;
    //统计
    Long count();
    //条件统计
    Long countByParams(TaskStepParams taskStepParams);
    //获取信息
    TaskStep getTaskStep(Long taskId) throws BaseException;
}
