package com.sky.skyserver.controller;

import com.sky.skyentity.bean.TaskHistoryParams;
import com.sky.skyserver.common.BaseDataResp;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyentity.entity.TaskHistory;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyserver.service.inter.TaskHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.sky.skyserver.common.BaseExceptionEnum.SESSION_LOGIN_EXPIRED;

@RestController
@RequestMapping("/history")
@Api(value = "任务历史管理", description = "任务历史管理API")
public class TaskHistoryController {
    private Logger logger = LoggerFactory.getLogger(TaskHistoryController.class);
    @Autowired
    TaskHistoryService taskHistoryService;
    //==============1、平台基础操作接口(不对普通用户开放)==============

    @ApiOperation(value = "新增(admin)", notes = "新增任务历史数据")
    @RequestMapping(value = "/admin/save", method = {RequestMethod.POST})
    public BaseDataResp save(TaskHistory taskHistory) {
        BaseDataResp baseDataResp;
        try {
            if (null == taskHistory)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            taskHistoryService.save(taskHistory);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "条件查询(admin)", notes = "按条件AND查询任务历史数据")
    @RequestMapping(value = "/admin/findByParams", method = {RequestMethod.POST})
    public BaseDataResp<List<TaskHistory>> findByParamsAdmin(TaskHistoryParams taskHistoryParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == taskHistoryParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<TaskHistory> taskHistoryList = taskHistoryService.findByParams(taskHistoryParams);
            baseDataResp = new BaseDataResp("查询成功",taskHistoryList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    //==================2、功能操作接口(对外开放)====================

    @ApiOperation(value = "条件查询", notes = "按条件AND查询任务历史数据")
    @RequestMapping(value = "/findByParams", method = {RequestMethod.POST})
    public BaseDataResp<List<TaskHistory>> findByParams(TaskHistoryParams taskHistoryParams, HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            if (null == taskHistoryParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<TaskHistory> taskHistoryList = taskHistoryService.findByParams(taskHistoryParams);
            baseDataResp = new BaseDataResp("查询成功",taskHistoryList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }
}
