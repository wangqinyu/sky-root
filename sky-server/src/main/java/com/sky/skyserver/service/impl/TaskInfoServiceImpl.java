package com.sky.skyserver.service.impl;

import com.sky.skyentity.bean.TaskInfoParams;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyserver.common.ConstantEnum;
import com.sky.skyserver.dao.ProjectInfoDao;
import com.sky.skyserver.dao.TaskHistoryDao;
import com.sky.skyserver.dao.TaskInfoDao;
import com.sky.skyserver.dao.UserInfoDao;
import com.sky.skyentity.entity.ProjectInfo;
import com.sky.skyentity.entity.TaskHistory;
import com.sky.skyentity.entity.TaskInfo;
import com.sky.skyentity.entity.UserInfo;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyentity.model.TaskInfo_;
import com.sky.skyserver.service.inter.TaskInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.sky.skyserver.common.ConstantEnum.*;

@Service
@Transactional
public class TaskInfoServiceImpl implements TaskInfoService {
    private Logger logger = LoggerFactory.getLogger(TaskInfoServiceImpl.class);
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    ProjectInfoDao projectInfoDao;
    @Autowired
    TaskInfoDao taskInfoDao;
    @Autowired
    TaskHistoryDao taskHistoryDao;

    /**
     * 新增任务
     * @param taskInfo
     * @throws BaseException
     */
    @Override
    public void save(TaskInfo taskInfo) throws BaseException {
        if (null != taskInfo.getTaskId())
            throw new BaseException(BaseExceptionEnum.TASK_EXISTED);
        taskInfo.setCreatedAt(new Date());//Mysql数据库版本提升至5.6.5+后，可删除此代码
        taskInfoDao.save(taskInfo);
    }

    /**
     * 查询全部任务信息
     * @return
     */
    @Override
    public List<TaskInfo> findAll() {
        return taskInfoDao.findAll();
    }

    /**
     * 按条件AND查询任务数据
     * @param taskInfoParams
     * @return
     */
    @Override
    public List<TaskInfo> findByParams(TaskInfoParams taskInfoParams) {
        //查询条件
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != taskInfoParams.getUserId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskInfo_.USER_ID), taskInfoParams.getUserId()));
            }
            if (StringUtils.isNotBlank(taskInfoParams.getUserGuid())) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskInfo_.USER_GUID), taskInfoParams.getUserGuid()));
            }
            if (null != taskInfoParams.getTaskId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskInfo_.TASK_ID), taskInfoParams.getTaskId()));
            }
            if (null != taskInfoParams.getProjectId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskInfo_.PROJECT_ID), taskInfoParams.getProjectId()));
            }
            if (null != taskInfoParams.getStatus()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskInfo_.STATUS), taskInfoParams.getStatus()));
            }
            if (null != taskInfoParams.getTaskPid()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskInfo_.TASK_PID), taskInfoParams.getTaskPid()));
            }
            if (null != taskInfoParams.getCreatedAt_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(TaskInfo_.CREATED_AT), taskInfoParams.getCreatedAt_start()));
            }
            if (null != taskInfoParams.getCreatedAt_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(TaskInfo_.CREATED_AT), taskInfoParams.getCreatedAt_end()));
            }
            Predicate predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.where(predicate).distinct(true);
            return null;
        };
        //分页和排序
        Pageable pageable;
        if (null != taskInfoParams.getPageAndSortParams()) {
            //分页
            Integer page = null;
            Integer size = null;
            if (null != taskInfoParams.getPageAndSortParams().getPage()) {
                page = taskInfoParams.getPageAndSortParams().getPage();
            }
            if (null != taskInfoParams.getPageAndSortParams().getSize()) {
                size = taskInfoParams.getPageAndSortParams().getSize();
            }
            //排序
            Sort sort = null;
            if (null != taskInfoParams.getPageAndSortParams().getSort() && taskInfoParams.getPageAndSortParams().getSort().size() > 0) {
                List<Sort.Order> orderList = new ArrayList<>();
                for (String key : taskInfoParams.getPageAndSortParams().getSort().keySet()) {
                    if (ConstantEnum.SORT_ASC.getStringCode().equals(taskInfoParams.getPageAndSortParams().getSort().get(key))) {
                        Sort.Order order = new Sort.Order(Sort.Direction.ASC, key);
                        orderList.add(order);
                    } else {
                        Sort.Order order = new Sort.Order(Sort.Direction.DESC, key);
                        orderList.add(order);
                    }
                }
                sort = Sort.by(orderList);
            }
            //判断是否需要分页排序
            if (null != sort && null != page && null != size) {
                //有分页有排序
                pageable = PageRequest.of(page, size, sort);
                return taskInfoDao.findAll(specification, pageable).getContent();
            } else if (null != sort && (null == page || null == size)) {
                //无分页有排序
                return taskInfoDao.findAll(specification, sort);
            } else if (null == sort && null != page && null != size) {
                //有分页无排序
                pageable = PageRequest.of(page, size);
                return taskInfoDao.findAll(specification, pageable).getContent();
            } else {
                //无分页无排序
                return taskInfoDao.findAll(specification);
            }
        } else {
            //无分页无排序
            return taskInfoDao.findAll(specification);
        }
    }

    /**
     * 领取任务
     * @param taskInfoParams
     * @throws BaseException
     */
    @Override
    public void claimTask(TaskInfoParams taskInfoParams) throws BaseException {
        //判断用户类型
        Optional<UserInfo> userInfoOptional = userInfoDao.findById(taskInfoParams.getUserId());
        if (!userInfoOptional.isPresent())
            throw new BaseException(BaseExceptionEnum.USER_NOT_EXISTED);
        //判断用户状态
        if (userInfoOptional.get().getStatus() != USER_STATUS_ACTIVE.getIntCode())
            throw new BaseException(BaseExceptionEnum.USER_STATUS_NOT_ONLINE);
        //设置用户信息
        taskInfoParams.setUserGuid(userInfoOptional.get().getUserGuid());
        //领取任务
        if (USER_TYPE_HEAD_OFFICE.getIntCode() == userInfoOptional.get().getType()){
            //总公司领取任务
            claimTaskHeadOffice(taskInfoParams);
        }else if (USER_TYPE_BRANCH_OFFICE.getIntCode() == userInfoOptional.get().getType()){
            //分公司领取任务
            claimTaskBranchOffice(taskInfoParams);
        }else if (USER_TYPE_EXTENSION_WORKER.getIntCode() == userInfoOptional.get().getType()){
            //推广员领取任务
            claimTaskExtensionWorker(taskInfoParams);
        }else{
            throw new BaseException(BaseExceptionEnum.USER_TYPE_NOT_SUPPORT);
        }
    }

    /**
     * HeadOffice领取任务
     * @param taskInfoParams
     * @throws BaseException
     */
    private void claimTaskHeadOffice(TaskInfoParams taskInfoParams) throws BaseException {
        //设置任务历史数据
        TaskHistory taskHistory = new TaskHistory();
        //从项目领取
        Optional<ProjectInfo> projectInfoOptional = projectInfoDao.findById(taskInfoParams.getProjectId());
        if (!projectInfoOptional.isPresent())
            throw new BaseException(BaseExceptionEnum.PROJECT_NOT_EXISTED);
        ProjectInfo projectInfoUpdate = projectInfoOptional.get();
        //判断项目状态
        if (projectInfoUpdate.getStatus() != PROJECT_STATUS_ONLINE.getIntCode())
            throw new BaseException(BaseExceptionEnum.PROJECT_STATUS_NOT_ONLINE);
        //判断库存
        if (projectInfoUpdate.getLeft() < taskInfoParams.getClaimAmount())
            throw new BaseException(BaseExceptionEnum.TASK_LEFT_NOT_ENOUGH);
        //查询领取历史
        TaskInfo params = new TaskInfo();
        params.setUserId(taskInfoParams.getUserId());
        params.setProjectId(taskInfoParams.getProjectId());
        Example<TaskInfo> example = Example.of(params);
        Optional<TaskInfo> taskInfoOptional = taskInfoDao.findOne(example);
        if (taskInfoOptional.isPresent()){
            TaskInfo taskInfoUpdate = taskInfoOptional.get();
            taskInfoUpdate.setAmount(taskInfoUpdate.getAmount() + taskInfoParams.getClaimAmount());
            taskInfoUpdate.setLeft(taskInfoUpdate.getLeft() + taskInfoParams.getClaimAmount());
            //更新任务数据
            taskInfoDao.saveAndFlush(taskInfoUpdate);
            //设置任务历史数据
            taskHistory.setTaskId(taskInfoUpdate.getTaskId());
        }else {
            //新增任务数据
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setUserId(taskInfoParams.getUserId());
            taskInfo.setUserGuid(taskInfoParams.getUserGuid());
            taskInfo.setProjectId(taskInfoParams.getProjectId());
            taskInfo.setStatus(TASK_STATUS_INIT.getIntCode());
            taskInfo.setAmount(taskInfoParams.getClaimAmount());
            taskInfo.setLeft(taskInfoParams.getClaimAmount());
            taskInfo.setCreatedAt(new Date());//mysql数据库版本升级至5.6.5+后，可删除本行
            TaskInfo infoReturn = taskInfoDao.save(taskInfo);
            //设置任务历史数据
            taskHistory.setTaskId(infoReturn.getTaskId());
        }
        //更新项目数据
        projectInfoUpdate.setLeft(projectInfoUpdate.getLeft() - taskInfoParams.getClaimAmount());
        projectInfoDao.saveAndFlush(projectInfoUpdate);
        //新增任务历史
        taskHistory.setUserId(taskInfoParams.getUserId());
        taskHistory.setProjectId(taskInfoParams.getProjectId());
        taskHistory.setAmount(taskInfoParams.getClaimAmount());
        taskHistory.setCreatedAt(new Date());//Mysql数据库版本提升至5.6.5+后，可删除此代码
        taskHistoryDao.save(taskHistory);
    }

    /**
     * BranchOffice领取任务
     * @param taskInfoParams
     * @throws BaseException
     */
    private void claimTaskBranchOffice(TaskInfoParams taskInfoParams) throws BaseException{
        //设置任务历史数据
        TaskHistory taskHistory = new TaskHistory();
        //查询项目数据
        Optional<ProjectInfo> projectInfoOptional = projectInfoDao.findById(taskInfoParams.getProjectId());
        if (!projectInfoOptional.isPresent())
            throw new BaseException(BaseExceptionEnum.PROJECT_NOT_EXISTED);
        ProjectInfo projectInfoUpdate = projectInfoOptional.get();
        //查询上级任务
        if (null == taskInfoParams.getTaskPid())
            throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
        Optional<TaskInfo> taskInfoOptional = taskInfoDao.findById(taskInfoParams.getTaskPid());
        if (!taskInfoOptional.isPresent())
            throw new BaseException(BaseExceptionEnum.TASK_NOT_EXISTED);
        TaskInfo taskInfoUpdate = taskInfoOptional.get();
        //判断是否本人创建的任务
        if (taskInfoUpdate.getUserId() == taskInfoParams.getUserId())
            throw new BaseException(BaseExceptionEnum.CLAIM_TASK_SELF_NOT_SUPPORT);
        //判断任务状态
        if (taskInfoUpdate.getStatus() == TASK_STATUS_CANCEL.getIntCode() || taskInfoUpdate.getStatus() == TASK_STATUS_DONE.getIntCode())
            throw new BaseException(BaseExceptionEnum.TASK_STATUS_NOT_ONLINE);
        //判断库存
        if (taskInfoUpdate.getLeft() < taskInfoParams.getClaimAmount())
            throw new BaseException(BaseExceptionEnum.TASK_LEFT_NOT_ENOUGH);
        //查询本人任务历史
        TaskInfo params = new TaskInfo();
        params.setUserId(taskInfoParams.getUserId());
        params.setProjectId(taskInfoParams.getProjectId());
        Example<TaskInfo> example = Example.of(params);
        Optional<TaskInfo> taskInfoOptionalUpdateSelf = taskInfoDao.findOne(example);
        if (taskInfoOptionalUpdateSelf.isPresent()){
            //更新本人任务数据
            TaskInfo taskInfoUpdateSelf = taskInfoOptionalUpdateSelf.get();
            taskInfoUpdateSelf.setAmount(taskInfoUpdateSelf.getAmount() + taskInfoParams.getClaimAmount());
            taskInfoUpdateSelf.setLeft(taskInfoUpdateSelf.getLeft() + taskInfoParams.getClaimAmount());
            taskInfoDao.saveAndFlush(taskInfoUpdateSelf);
            //设置任务历史数据
            taskHistory.setTaskId(taskInfoUpdateSelf.getTaskId());
        }else {
            //新增本人任务数据
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setUserId(taskInfoParams.getUserId());
            taskInfo.setUserGuid(taskInfoParams.getUserGuid());
            taskInfo.setProjectId(taskInfoParams.getProjectId());
            taskInfo.setTaskPid(taskInfoParams.getTaskPid());
            taskInfo.setStatus(TASK_STATUS_INIT.getIntCode());
            taskInfo.setAmount(taskInfoParams.getClaimAmount());
            taskInfo.setLeft(taskInfoParams.getClaimAmount());
            taskInfo.setCreatedAt(new Date());//mysql数据库版本升级至5.6.5+后，可删除本行
            TaskInfo infoReturn = taskInfoDao.save(taskInfo);
            //设置任务历史数据
            taskHistory.setTaskId(infoReturn.getTaskId());
        }
        //判断项目状态
        if (projectInfoUpdate.getStatus() != PROJECT_STATUS_ONLINE.getIntCode())
            throw new BaseException(BaseExceptionEnum.PROJECT_STATUS_NOT_ONLINE);
        //更新项目数据
        projectInfoUpdate.setLeft(projectInfoUpdate.getLeft() - taskInfoParams.getClaimAmount());
        projectInfoDao.saveAndFlush(projectInfoUpdate);
        //更新上级任务库存
        taskInfoUpdate.setLeft(taskInfoUpdate.getLeft() - taskInfoParams.getClaimAmount());
        taskInfoDao.saveAndFlush(taskInfoUpdate);
        //新增任务历史
        taskHistory.setUserId(taskInfoParams.getUserId());
        taskHistory.setProjectId(taskInfoParams.getProjectId());
        taskHistory.setAmount(taskInfoParams.getClaimAmount());
        taskHistory.setCreatedAt(new Date());//Mysql数据库版本提升至5.6.5+后，可删除此代码
        taskHistoryDao.save(taskHistory);
    }

    /**
     * ExtensionWorker领取任务
     * @param taskInfoParams
     * @throws BaseException
     */
    private void claimTaskExtensionWorker(TaskInfoParams taskInfoParams) throws BaseException{
        claimTaskBranchOffice(taskInfoParams);
    }

    /**
     * 获取任务信息
     * @param taskId
     * @return
     * @throws BaseException
     */
    @Override
    public TaskInfo getTaskInfo(Long taskId) throws BaseException {
        Optional<TaskInfo> taskInfoOptional = taskInfoDao.findById(taskId);
        if (!taskInfoOptional.isPresent())
            throw new BaseException(BaseExceptionEnum.TASK_NOT_EXISTED);
        return taskInfoOptional.get();
    }

    /**
     * 更改任务状态
     * @param taskInfoParams
     * @throws BaseException
     */
    @Override
    public void updateStatus(TaskInfoParams taskInfoParams) throws BaseException {
        Optional<TaskInfo> taskInfoOptional = taskInfoDao.findById(taskInfoParams.getTaskId());
        if (!taskInfoOptional.isPresent())
            throw new BaseException(BaseExceptionEnum.TASK_NOT_EXISTED);
        TaskInfo taskInfo = taskInfoOptional.get();
        taskInfo.setStatus(taskInfoParams.getStatus());
        taskInfoDao.saveAndFlush(taskInfo);
    }

    //给某一级增加任务量时刷新父级以上库存
    public void refreshParentAmount(Long claimAmount,Long taskPid){
        while (-1 != taskPid){
            Optional<TaskInfo> taskInfoParentOptional = taskInfoDao.findById(taskPid);
            if (!taskInfoParentOptional.isPresent())
                break;
            TaskInfo taskInfoParent = taskInfoParentOptional.get();
            taskInfoParent.setLeft(taskInfoParent.getLeft() + claimAmount);
            taskInfoDao.saveAndFlush(taskInfoParent);
            taskPid = taskInfoParent.getTaskPid();
        }
    }
}
