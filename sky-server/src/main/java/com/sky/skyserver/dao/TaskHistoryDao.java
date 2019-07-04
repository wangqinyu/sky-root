package com.sky.skyserver.dao;

import com.sky.skyentity.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskHistoryDao extends JpaRepository<TaskHistory, Long> , JpaSpecificationExecutor {

}
