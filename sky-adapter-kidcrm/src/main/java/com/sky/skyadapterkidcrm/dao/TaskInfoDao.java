package com.sky.skyadapterkidcrm.dao;

import com.sky.skyentity.entity.TaskInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskInfoDao extends JpaRepository<TaskInfo, Long> , JpaSpecificationExecutor {
    List<TaskInfo> findTaskInfosByProjectIdAndStatusIsNot(Long projectId, Integer status);
}
