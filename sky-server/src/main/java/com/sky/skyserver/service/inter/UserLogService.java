package com.sky.skyserver.service.inter;

import com.sky.skyentity.bean.UserLogParams;
import com.sky.skyentity.entity.UserLog;
import com.sky.skyserver.exception.BaseException;

import java.util.List;

public interface UserLogService {
    //新增日志
    void save(UserLog userLog) throws BaseException;
    //按条件查询日志
    List<UserLog> findByParams(UserLogParams userLogParams);
}
