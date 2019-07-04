package com.sky.skyserver.controller;

import com.sky.skyentity.bean.TaskResultParams;
import com.sky.skyserver.common.BaseDataResp;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyentity.entity.TaskResult;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyserver.service.inter.TaskResultService;
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
@RequestMapping("/result")
@Api(value = "任务结果管理", description = "任务结果管理API")
public class TaskResultController {
    private Logger logger = LoggerFactory.getLogger(TaskResultController.class);
    @Autowired
    TaskResultService taskResultService;
    //==============1、平台基础操作接口(不对普通用户开放)==============

    //==================2、功能操作接口(对外开放)====================
    @ApiOperation(value = "条件查询", notes = "按条件AND查询任务结果数据")
    @RequestMapping(value = "/findByParams", method = {RequestMethod.POST})
    public BaseDataResp<List<TaskResult>> findByParamsAdmin(TaskResultParams taskResultParams, HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            if (null == taskResultParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<TaskResult> taskResultList = taskResultService.findByParams(taskResultParams);
            baseDataResp = new BaseDataResp("查询成功",taskResultList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "可选条件查询", notes = "通过多个可选条件OR查询任务结果数据")
    @RequestMapping(value = "/findByOptional", method = {RequestMethod.POST})
    public BaseDataResp<List<TaskResult>> findByOptional(TaskResultParams taskResultParams,HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            if (null == taskResultParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<TaskResult> taskResultList = taskResultService.findByOptional(taskResultParams);
            baseDataResp = new BaseDataResp("查询成功",taskResultList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "统计", notes = "统计任务结果量")
    @RequestMapping(value = "/count", method = {RequestMethod.POST})
    public BaseDataResp count(HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            Long taskAmount = taskResultService.count();
            baseDataResp = new BaseDataResp("操作成功",taskAmount);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "条件统计", notes = "根据条件统计任务结果量")
    @RequestMapping(value = "/countByParams", method = {RequestMethod.POST})
    public BaseDataResp countByParams(TaskResultParams taskResultParams,HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            if (null == taskResultParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            Long taskAmount = taskResultService.countByParams(taskResultParams);
            baseDataResp = new BaseDataResp("操作成功",taskAmount);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

}
