package com.sky.skyserver.controller;

import com.sky.skyentity.bean.TaskStepParams;
import com.sky.skyserver.common.BaseDataResp;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyentity.entity.TaskStep;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyserver.service.inter.TaskStepService;
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
@RequestMapping("/step")
@Api(value = "任务指标管理", description = "任务指标管理API")
public class TaskStepController {
    private Logger logger = LoggerFactory.getLogger(TaskStepController.class);
    @Autowired
    TaskStepService taskStepService;
    //==============平台基础操作接口(不对普通用户开放)==============
    @ApiOperation(value = "新增(admin)", notes = "新增任务指标数据")
    @RequestMapping(value = "/admin/save", method = {RequestMethod.POST})
    public BaseDataResp save(TaskStep taskStep) {
        BaseDataResp baseDataResp;
        try {
            if (null == taskStep)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            taskStepService.save(taskStep);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "查询全部(admin)", notes = "查询全部任务指标数据")
    @RequestMapping(value = "/admin/findAll", method = {RequestMethod.POST})
    public BaseDataResp<List<TaskStep>> findAll() {
        BaseDataResp baseDataResp;
        try {
            List<TaskStep> taskStepList = taskStepService.findAll();
            baseDataResp = new BaseDataResp("查询成功",taskStepList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "条件查询(admin)", notes = "按条件AND查询任务指标数据")
    @RequestMapping(value = "/admin/findByParams", method = {RequestMethod.POST})
    public BaseDataResp<List<TaskStep>> findByParamsAdmin(TaskStepParams taskStepParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == taskStepParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<TaskStep> taskStepList = taskStepService.findByParams(taskStepParams);
            baseDataResp = new BaseDataResp("查询成功",taskStepList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }
    @ApiOperation(value = "更新(admin)", notes = "更新任务指标数据")
    @RequestMapping(value = "/admin/update", method = {RequestMethod.POST})
    public BaseDataResp update(TaskStep taskStep) {
        BaseDataResp baseDataResp;
        try {
            if (null == taskStep)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            taskStepService.update(taskStep);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "可选条件查询(admin)", notes = "通过多个可选条件OR查询任务指标数据")
    @RequestMapping(value = "/admin/findByOptional", method = {RequestMethod.POST})
    public BaseDataResp<List<TaskStep>> findByOptional(TaskStepParams taskStepParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == taskStepParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<TaskStep> taskStepList = taskStepService.findByOptional(taskStepParams);
            baseDataResp = new BaseDataResp("查询成功",taskStepList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "删除(admin)", notes = "删除任务指标数据")
    @RequestMapping(value = "/admin/delete", method = {RequestMethod.POST})
    public BaseDataResp delete(Long taskId) {
        BaseDataResp baseDataResp;
        try {
            taskStepService.delete(taskId);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "统计(admin)", notes = "统计任务指标量")
    @RequestMapping(value = "/admin/count", method = {RequestMethod.POST})
    public BaseDataResp count() {
        BaseDataResp baseDataResp;
        try {
            Long taskAmount = taskStepService.count();
            baseDataResp = new BaseDataResp("操作成功",taskAmount);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "条件统计(admin)", notes = "根据条件统计任务指标量")
    @RequestMapping(value = "/admin/countByParams", method = {RequestMethod.POST})
    public BaseDataResp countByParams(TaskStepParams taskStepParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == taskStepParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            Long taskAmount = taskStepService.countByParams(taskStepParams);
            baseDataResp = new BaseDataResp("操作成功",taskAmount);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "获取任务指标信息(admin)", notes = "获取任务指标信息")
    @RequestMapping(value = "/admin/getTaskStep", method = {RequestMethod.POST})
    public BaseDataResp getTaskStepAdmin(Long taskId) {
        BaseDataResp baseDataResp;
        try {
            TaskStep taskStep = taskStepService.getTaskStep(taskId);
            baseDataResp = new BaseDataResp("查询成功",taskStep);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    //==================2、功能操作接口(对外开放)====================

    @ApiOperation(value = "条件查询", notes = "按条件AND查询任务指标数据")
    @RequestMapping(value = "/findByParams", method = {RequestMethod.POST})
    public BaseDataResp<List<TaskStep>> findByParams(TaskStepParams taskStepParams, HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            if (null == taskStepParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<TaskStep> taskStepList = taskStepService.findByParams(taskStepParams);
            baseDataResp = new BaseDataResp("查询成功",taskStepList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "获取任务指标信息", notes = "获取任务指标信息")
    @RequestMapping(value = "/getTaskStep", method = {RequestMethod.POST})
    public BaseDataResp getTaskStep(Long taskId,HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            TaskStep taskStep = taskStepService.getTaskStep(taskId);
            baseDataResp = new BaseDataResp("查询成功",taskStep);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

}
