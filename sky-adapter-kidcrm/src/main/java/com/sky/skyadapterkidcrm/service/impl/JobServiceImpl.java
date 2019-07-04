package com.sky.skyadapterkidcrm.service.impl;

import com.sky.skyadapterkidcrm.service.inter.JobService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Map;
@Service
@Transactional
public class JobServiceImpl implements JobService {
    Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
    @Autowired
    private Scheduler scheduler;

    private static Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job) class1.newInstance();
    }

    /**
     * 新增定时任务
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @param jobDescription
     * @param params
     * @throws Exception
     */
    @Override
    public void addJob(String jobClassName, String jobGroupName, String cronExpression, String jobDescription,
                       Map<String, Object> params) throws Exception {
        // 启动调度器
        scheduler.start();
        // 构建job信息
        JobDetail jobDetail = JobBuilder.newJob(JobServiceImpl.getClass(jobClassName).getClass())
                .withIdentity(jobClassName, jobGroupName).withDescription(jobDescription).build();
        if (null != params){
            Iterator<Map.Entry<String, Object>> var7 = params.entrySet().iterator();
            while(var7.hasNext()) {
                Map.Entry<String, Object> entry = var7.next();
                jobDetail.getJobDataMap().put((String)entry.getKey(), entry.getValue());
            }
        }
        logger.info("jobDetail数据："+jobDetail.toString());
        // 表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
                .withSchedule(scheduleBuilder).build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("创建定时任务成功");

        } catch (SchedulerException e) {
            logger.error("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
    }

    /**
     * 删除定时任务
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @Override
    public void removeJob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
        logger.info("删除任务成功");
    }

    /**
     * 暂停定时任务
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @Override
    public void pauseJob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
        logger.info("任务已暂停");
    }

    /**
     * 恢复定时任务
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @Override
    public void resumejob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
        logger.info("任务已恢复");
    }
}
