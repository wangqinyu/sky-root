package com.sky.skyadapterkidcrm.service.impl;

import com.sky.skyadapterkidcrm.bean.*;
import com.sky.skyadapterkidcrm.common.BaseExceptionEnum;
import com.sky.skyadapterkidcrm.common.ConstantEnum;
import com.sky.skyadapterkidcrm.dao.TaskInfoDao;
import com.sky.skyadapterkidcrm.dao.TaskResultDao;
import com.sky.skyentity.entity.TaskInfo;
import com.sky.skyentity.entity.TaskResult;
import com.sky.skyadapterkidcrm.exception.BaseException;
import com.sky.skyadapterkidcrm.feign.AdapterFeign;
import com.sky.skyadapterkidcrm.service.inter.AdapterService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.sky.skyadapterkidcrm.util.CrmUtil.getSign;
import static com.sky.skyadapterkidcrm.util.DateUtils.currentTime;
import static com.sky.skyadapterkidcrm.util.DateUtils.getYesterday;

@Service
@Transactional
public class AdapterServiceImpl implements AdapterService {
    @Autowired
    AdapterFeign adapterFeign;

    @Autowired
    TaskInfoDao taskInfoDao;

    @Autowired
    TaskResultDao taskResultDao;

    /**
     * 查询用户数据
     * @param userGuid
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    @Override
    public KidcrmResp searchOne(String userGuid,String startTime,String endTime) throws Exception {
        KidcrmBodyReq kidcrmBodyReq = new KidcrmBodyReq(userGuid,startTime,endTime);
        KidcrmReq kidcrmReq = new KidcrmReq(kidcrmBodyReq);
        String sign = getSign(kidcrmReq);
        kidcrmReq.setSign(sign);
        KidcrmResp kidcrmResp = adapterFeign.searchOne(kidcrmReq);
        return kidcrmResp;
    }

    /**
     * 定时任务获取数据
     * @param projectId
     * @throws Exception
     */
    @Override
    public void fetchAllWithJob(Long projectId) throws Exception {
        //查询任务信息
        if (null == projectId)
            throw new BaseException(BaseExceptionEnum.TASK_NOT_EXISTED);
        List<TaskInfo> taskInfoList = taskInfoDao.findTaskInfosByProjectIdAndStatusIsNot(projectId,ConstantEnum.TASK_STATUS_CANCEL.getIntCode());
        if (null == taskInfoList || taskInfoList.size() <= 0)
            throw new BaseException(BaseExceptionEnum.TASK_NOT_EXISTED);
        //获取数据
        for (TaskInfo taskInfo : taskInfoList) {
            if (StringUtils.isBlank(taskInfo.getUserGuid()))
                throw new BaseException(BaseExceptionEnum.USER_NOT_EXISTED);
            KidcrmResp kidcrmResp = searchOne(taskInfo.getUserGuid(), getYesterday(), currentTime());
            //保存任务数据
            if (ConstantEnum.SUCCESS.getStringCode().equals(kidcrmResp.getCode()) && null != kidcrmResp.getBody()){
                //L1
                if (StringUtils.isNotBlank(kidcrmResp.getBody().getRegistrationAmount()) && !kidcrmResp.getBody().getRegistrationAmount().equals("0")) {
                    TaskResult taskResult = new TaskResult();
                    taskResult.setIndex(1);
                    taskResult.setProjectId(taskInfo.getProjectId());
                    taskResult.setUserId(taskInfo.getUserId());
                    taskResult.setUserGuid(taskInfo.getUserGuid());
                    taskResult.setTaskId(taskInfo.getTaskId());
                    taskResult.setAmount(Long.parseLong(kidcrmResp.getBody().getRegistrationAmount()));
                    taskResult.setTaskTime(new Date());
                    taskResultDao.saveAndFlush(taskResult);
                }
                //L2
                if (StringUtils.isNotBlank(kidcrmResp.getBody().getEffectiveAmount()) && !kidcrmResp.getBody().getEffectiveAmount().equals("0")) {
                    TaskResult taskResult = new TaskResult();
                    taskResult.setIndex(2);
                    taskResult.setProjectId(taskInfo.getProjectId());
                    taskResult.setUserId(taskInfo.getUserId());
                    taskResult.setUserGuid(taskInfo.getUserGuid());
                    taskResult.setTaskId(taskInfo.getTaskId());
                    taskResult.setAmount(Long.parseLong(kidcrmResp.getBody().getEffectiveAmount()));
                    taskResult.setTaskTime(new Date());
                    taskResultDao.saveAndFlush(taskResult);
                }
                //L3
                if (StringUtils.isNotBlank(kidcrmResp.getBody().getVisitAmount()) && !kidcrmResp.getBody().getVisitAmount().equals("0")) {
                    TaskResult taskResult = new TaskResult();
                    taskResult.setIndex(3);
                    taskResult.setProjectId(taskInfo.getProjectId());
                    taskResult.setUserId(taskInfo.getUserId());
                    taskResult.setUserGuid(taskInfo.getUserGuid());
                    taskResult.setTaskId(taskInfo.getTaskId());
                    taskResult.setAmount(Long.parseLong(kidcrmResp.getBody().getVisitAmount()));
                    taskResult.setTaskTime(new Date());
                    taskResultDao.saveAndFlush(taskResult);
                }
                //L4
                if (StringUtils.isNotBlank(kidcrmResp.getBody().getAuditionAmount()) && !kidcrmResp.getBody().getAuditionAmount().equals("0")) {
                    TaskResult taskResult = new TaskResult();
                    taskResult.setIndex(4);
                    taskResult.setProjectId(taskInfo.getProjectId());
                    taskResult.setUserId(taskInfo.getUserId());
                    taskResult.setUserGuid(taskInfo.getUserGuid());
                    taskResult.setTaskId(taskInfo.getTaskId());
                    taskResult.setAmount(Long.parseLong(kidcrmResp.getBody().getAuditionAmount()));
                    taskResult.setTaskTime(new Date());
                    taskResultDao.saveAndFlush(taskResult);
                }
                //L5
                if (StringUtils.isNotBlank(kidcrmResp.getBody().getPaymentAmount()) && !kidcrmResp.getBody().getPaymentAmount().equals("0")) {
                    TaskResult taskResult = new TaskResult();
                    taskResult.setIndex(5);
                    taskResult.setProjectId(taskInfo.getProjectId());
                    taskResult.setUserId(taskInfo.getUserId());
                    taskResult.setUserGuid(taskInfo.getUserGuid());
                    taskResult.setTaskId(taskInfo.getTaskId());
                    taskResult.setAmount(Long.parseLong(kidcrmResp.getBody().getPaymentAmount()));
                    taskResult.setTaskTime(new Date());
                    taskResultDao.saveAndFlush(taskResult);
                }
                //L6
                if (StringUtils.isNotBlank(kidcrmResp.getBody().getPaymentMoney()) && !kidcrmResp.getBody().getPaymentMoney().equals("0")) {
                    TaskResult taskResult = new TaskResult();
                    taskResult.setIndex(6);
                    taskResult.setProjectId(taskInfo.getProjectId());
                    taskResult.setUserId(taskInfo.getUserId());
                    taskResult.setUserGuid(taskInfo.getUserGuid());
                    taskResult.setTaskId(taskInfo.getTaskId());
                    taskResult.setAmount(Long.parseLong(kidcrmResp.getBody().getPaymentMoney()));
                    taskResult.setTaskTime(new Date());
                    taskResultDao.saveAndFlush(taskResult);
                }
            }
        }
    }
}
