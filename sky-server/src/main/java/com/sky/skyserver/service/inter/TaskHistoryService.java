package com.sky.skyserver.service.inter;

import com.sky.skyentity.bean.TaskHistoryParams;
import com.sky.skyentity.entity.TaskHistory;

import java.util.List;

public interface TaskHistoryService {
    //新增任务历史数据
    void save(TaskHistory taskHistory);
    //按条件查询任务历史数据
    List<TaskHistory> findByParams(TaskHistoryParams taskHistoryParams);
}
