package com.sky.skyserver.dao;

import com.sky.skyentity.entity.TaskInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskInfoDao extends JpaRepository<TaskInfo, Long> , JpaSpecificationExecutor {

}
