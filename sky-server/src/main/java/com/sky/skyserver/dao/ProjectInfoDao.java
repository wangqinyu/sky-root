package com.sky.skyserver.dao;

import com.sky.skyentity.entity.ProjectInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectInfoDao extends JpaRepository<ProjectInfo, Long> , JpaSpecificationExecutor {

}
