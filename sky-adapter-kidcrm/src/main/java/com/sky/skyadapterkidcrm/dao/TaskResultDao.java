package com.sky.skyadapterkidcrm.dao;

import com.sky.skyentity.entity.TaskResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskResultDao extends JpaRepository<TaskResult, Long> , JpaSpecificationExecutor {

}
