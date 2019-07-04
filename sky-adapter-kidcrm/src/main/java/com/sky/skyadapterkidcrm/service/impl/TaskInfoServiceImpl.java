package com.sky.skyadapterkidcrm.service.impl;

import com.sky.skyadapterkidcrm.common.BaseExceptionEnum;
import com.sky.skyadapterkidcrm.common.ConstantEnum;
import com.sky.skyadapterkidcrm.dao.TaskInfoDao;
import com.sky.skyentity.bean.TaskInfoParams;
import com.sky.skyentity.entity.TaskInfo;
import com.sky.skyadapterkidcrm.exception.BaseException;
import com.sky.skyentity.model.TaskInfo_;
import com.sky.skyadapterkidcrm.service.inter.TaskInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskInfoServiceImpl implements TaskInfoService {
    private Logger logger = LoggerFactory.getLogger(TaskInfoServiceImpl.class);
    @Autowired
    TaskInfoDao taskInfoDao;

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

}
