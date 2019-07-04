package com.sky.skyserver.dao;

import com.sky.skyentity.entity.TaskStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStepDao extends JpaRepository<TaskStep, Long> , JpaSpecificationExecutor {

}
