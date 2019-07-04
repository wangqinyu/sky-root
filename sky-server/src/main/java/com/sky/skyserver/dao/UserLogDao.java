package com.sky.skyserver.dao;

import com.sky.skyentity.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogDao extends JpaRepository<UserLog, Long> , JpaSpecificationExecutor {

}
