package com.sky.skyserver.controller;

import com.sky.skyentity.bean.TaskInfoParams;
import com.sky.skyserver.common.BaseDataResp;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyentity.entity.TaskInfo;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyserver.service.inter.TaskInfoService;
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
@RequestMapping("/task")
@Api(value = "任务管理", description = "任务管理API")
public class TaskController {
    private Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    TaskInfoService taskInfoService;
    //==============平台基础操作接口(不对普通用户开放)==============
    @ApiOperation(value = "新增(admin)", notes = "新增任务数据")
    @RequestMapping(value = "/admin/save", method = {RequestMethod.POST})
    public BaseDataResp save(TaskInfo taskInfo) {
        BaseDataResp baseDataResp;
        try {
            if (null == taskInfo)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            taskInfoService.save(taskInfo);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "查询全部(admin)", notes = "查询全部任务数据")
    @RequestMapping(value = "/admin/findAll", method = {RequestMethod.POST})
    public BaseDataResp<List<TaskInfo>> findAll() {
        BaseDataResp baseDataResp;
        try {
            List<TaskInfo> taskInfoList = taskInfoService.findAll();
            baseDataResp = new BaseDataResp("查询成功",taskInfoList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "条件查询(admin)", notes = "按条件AND查询任务数据")
    @RequestMapping(value = "/admin/findByParams", method = {RequestMethod.POST})
    public BaseDataResp<List<TaskInfo>> findByParamsAdmin(TaskInfoParams taskInfoParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == taskInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<TaskInfo> taskInfoList = taskInfoService.findByParams(taskInfoParams);
            baseDataResp = new BaseDataResp("查询成功",taskInfoList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }
/*
    @ApiOperation(value = "更新(admin)", notes = "更新任务数据")
    @RequestMapping(value = "/admin/update", method = {RequestMethod.POST})
    public BaseDataResp update(TaskInfo taskInfo) {
        BaseDataResp baseDataResp;
        try {
            if (null == taskInfo)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            taskInfoService.update(taskInfo);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }





    @ApiOperation(value = "可选条件查询(admin)", notes = "通过多个可选条件OR查询任务数据")
    @RequestMapping(value = "/admin/findByOptional", method = {RequestMethod.POST})
    public BaseDataResp<List<TaskInfo>> findByOptional(TaskInfoParams taskInfoParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == taskInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<TaskInfo> taskInfoList = taskInfoService.findByOptional(taskInfoParams);
            baseDataResp = new BaseDataResp("查询成功",taskInfoList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "删除(admin)", notes = "删除任务数据")
    @RequestMapping(value = "/admin/delete", method = {RequestMethod.POST})
    public BaseDataResp delete(Long taskId) {
        BaseDataResp baseDataResp;
        try {
            taskInfoService.delete(taskId);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "统计(admin)", notes = "统计任务量")
    @RequestMapping(value = "/admin/count", method = {RequestMethod.POST})
    public BaseDataResp count() {
        BaseDataResp baseDataResp;
        try {
            Long taskAmount = taskInfoService.count();
            baseDataResp = new BaseDataResp("操作成功",taskAmount);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "条件统计(admin)", notes = "根据条件统计任务量")
    @RequestMapping(value = "/admin/countByParams", method = {RequestMethod.POST})
    public BaseDataResp countByParams(TaskInfoParams taskInfoParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == taskInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            Long taskAmount = taskInfoService.countByParams(taskInfoParams);
            baseDataResp = new BaseDataResp("操作成功",taskAmount);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "获取任务信息(admin)", notes = "获取任务信息")
    @RequestMapping(value = "/admin/getTaskInfo", method = {RequestMethod.POST})
    public BaseDataResp getTaskInfoAdmin(Long taskId) {
        BaseDataResp baseDataResp;
        try {
            TaskInfo taskInfo = taskInfoService.getTaskInfo(taskId);
            baseDataResp = new BaseDataResp("查询成功",taskInfo);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

*/
    //==================2、功能操作接口(对外开放)====================

    @ApiOperation(value = "领取任务", notes = "领取任务")
    @RequestMapping(value = "/claimTask", method = {RequestMethod.POST})
    public BaseDataResp claimTask(TaskInfoParams taskInfoParams, HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            if (null == taskInfoParams || null == taskInfoParams.getUserId() || null == taskInfoParams.getUserGuid() || null == taskInfoParams.getProjectId() || null == taskInfoParams.getClaimAmount())
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            taskInfoService.claimTask(taskInfoParams);
            baseDataResp = new BaseDataResp("领取成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "获取任务信息", notes = "获取任务信息")
    @RequestMapping(value = "/getTaskInfo", method = {RequestMethod.POST})
    public BaseDataResp getTaskInfo(Long taskId,HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            if (null == taskId)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            TaskInfo taskInfo = taskInfoService.getTaskInfo(taskId);
            baseDataResp = new BaseDataResp("查询成功",taskInfo);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "条件查询", notes = "按条件AND查询任务数据")
    @RequestMapping(value = "/findByParams", method = {RequestMethod.POST})
    public BaseDataResp<List<TaskInfo>> findByParams(TaskInfoParams taskInfoParams,HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            if (null == taskInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<TaskInfo> taskInfoList = taskInfoService.findByParams(taskInfoParams);
            baseDataResp = new BaseDataResp("查询成功",taskInfoList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "更改任务状态", notes = "更改任务状态")
    @RequestMapping(value = "/resetTaskStatus", method = {RequestMethod.POST})
    public BaseDataResp resetTaskStatus(TaskInfoParams taskInfoParams,HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            if (null == taskInfoParams || null == taskInfoParams.getTaskId() || null == taskInfoParams.getStatus())
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            taskInfoService.updateStatus(taskInfoParams);
            baseDataResp = new BaseDataResp("状态更新成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }
}
