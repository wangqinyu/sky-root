package com.sky.skyadapterkidcrm.job;

import com.sky.skyadapterkidcrm.service.inter.AdapterService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.UUID;

import static com.sky.skyadapterkidcrm.util.DateUtils.currentTime;

public class TaskJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(TaskJob.class);
    @Autowired
    AdapterService adapterService;
    @Value(value = "${common.kidcrm.projectId}")
    private Long projectId;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("[任务]--"+UUID.randomUUID().toString() + "--时间：" + currentTime());
        try {
            adapterService.fetchAllWithJob(projectId);
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                logger.info("今日任务已执行完毕");
            }else {
                e.printStackTrace();
                logger.error("[任务错误]--错误项目ID：" + projectId);
            }
        }
    }
}
