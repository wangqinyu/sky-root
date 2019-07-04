package com.sky.skyadapterkidcrm.controller;

import com.sky.skyadapterkidcrm.bean.KidcrmResp;
import com.sky.skyadapterkidcrm.common.BaseDataResp;
import com.sky.skyadapterkidcrm.common.BaseExceptionEnum;
import com.sky.skyadapterkidcrm.exception.BaseException;
import com.sky.skyadapterkidcrm.job.TaskJob;
import com.sky.skyadapterkidcrm.service.inter.AdapterService;
import com.sky.skyadapterkidcrm.service.inter.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/kidcrm")
@Api(value = "适配器管理", description = "适配器管理API")
public class AdapterController {
    private Logger logger = LoggerFactory.getLogger(AdapterController.class);

    @Autowired
    AdapterService adapterService;
    @Autowired
    JobService jobService;

    @ApiOperation(value = "查询数据", notes = "查询数据")
    @RequestMapping(value = "/searchOne", method = RequestMethod.POST)
    public BaseDataResp searchOne(String userGuid,String startTime,String endTime) {
        BaseDataResp baseDataResp;
        try {
            if (StringUtils.isBlank(userGuid))
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            KidcrmResp result = adapterService.searchOne(userGuid,startTime,endTime);
            baseDataResp = new BaseDataResp("操作成功",result);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "新增任务", notes = "新增任务")
    @RequestMapping(value = "/addJob", method = RequestMethod.POST)
    public BaseDataResp addJob(String cron,String groupName) {
        BaseDataResp baseDataResp;
        try {
            if (StringUtils.isBlank(cron) || StringUtils.isBlank(groupName))
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            jobService.addJob(TaskJob.class.getName(),groupName,cron,"动态任务",null);
            baseDataResp = new BaseDataResp("创建定时任务成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "删除任务", notes = "删除任务")
    @RequestMapping(value = "/removeJob", method = RequestMethod.POST)
    public BaseDataResp removeJob(String groupName) {
        BaseDataResp baseDataResp;
        try {
            if (StringUtils.isBlank(groupName))
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            jobService.removeJob(TaskJob.class.getName(),groupName);
            baseDataResp = new BaseDataResp("删除任务成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "暂停任务", notes = "暂停任务")
    @RequestMapping(value = "/pauseJob", method = RequestMethod.POST)
    public BaseDataResp pauseJob(String groupName) {
        BaseDataResp baseDataResp;
        try {
            if (StringUtils.isBlank(groupName))
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            jobService.pauseJob(TaskJob.class.getName(),groupName);
            baseDataResp = new BaseDataResp("任务已暂停");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "恢复任务", notes = "恢复任务")
    @RequestMapping(value = "/resumejob", method = RequestMethod.POST)
    public BaseDataResp resumejob(String groupName) {
        BaseDataResp baseDataResp;
        try {
            if (StringUtils.isBlank(groupName))
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            jobService.resumejob(TaskJob.class.getName(),groupName);
            baseDataResp = new BaseDataResp("任务已恢复");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

}
