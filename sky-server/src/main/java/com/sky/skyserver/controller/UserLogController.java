package com.sky.skyserver.controller;

import com.sky.skyentity.bean.UserLogParams;
import com.sky.skyserver.common.BaseDataResp;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyentity.entity.UserLog;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyserver.service.inter.UserLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/userLog")
@Api(value = "用户日志管理", description = "用户日志管理API")
public class UserLogController {
    private Logger logger = LoggerFactory.getLogger(UserLogController.class);
    @Autowired
    UserLogService userLogService;
    //==============1、平台基础操作接口(不对普通用户开放)==============

    @ApiOperation(value = "新增(admin)", notes = "新增用户日志数据")
    @RequestMapping(value = "/admin/save", method = {RequestMethod.POST})
    public BaseDataResp save(UserLog userLog) {
        BaseDataResp baseDataResp;
        try {
            if (null == userLog)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            userLogService.save(userLog);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "条件查询(admin)", notes = "按条件AND查询用户日志数据")
    @RequestMapping(value = "/admin/findByParams", method = {RequestMethod.POST})
    public BaseDataResp<List<UserLog>> findByParams(UserLogParams userLogParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == userLogParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<UserLog> userLogList = userLogService.findByParams(userLogParams);
            baseDataResp = new BaseDataResp("查询成功",userLogList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }
}
