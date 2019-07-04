package com.sky.skyserver.service.impl;

import com.sky.skyentity.bean.TaskResultParams;
import com.sky.skyserver.common.ConstantEnum;
import com.sky.skyserver.dao.TaskResultDao;
import com.sky.skyentity.entity.TaskResult;
import com.sky.skyentity.model.TaskResult_;
import com.sky.skyserver.service.inter.TaskResultService;
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
@Service
@Transactional
public class TaskResultServiceImpl implements TaskResultService {
    private Logger logger = LoggerFactory.getLogger(TaskResultServiceImpl.class);

    @Autowired
    TaskResultDao taskResultDao;

    /**
     * 条件查询
     * @param taskResultParams
     * @return
     */
    @Override
    public List<TaskResult> findByParams(TaskResultParams taskResultParams) {
        //查询条件
        Specification specification = specificationHandler(taskResultParams);
        //分页和排序
        Pageable pageable;
        if (null != taskResultParams.getPageAndSortParams()) {
            //分页
            Integer page = null;
            Integer size = null;
            if (null != taskResultParams.getPageAndSortParams().getPage()) {
                page = taskResultParams.getPageAndSortParams().getPage();
            }
            if (null != taskResultParams.getPageAndSortParams().getSize()) {
                size = taskResultParams.getPageAndSortParams().getSize();
            }
            //排序
            Sort sort = null;
            if (null != taskResultParams.getPageAndSortParams().getSort() && taskResultParams.getPageAndSortParams().getSort().size() > 0) {
                List<Sort.Order> orderList = new ArrayList<>();
                for (String key : taskResultParams.getPageAndSortParams().getSort().keySet()) {
                    if (ConstantEnum.SORT_ASC.getStringCode().equals(taskResultParams.getPageAndSortParams().getSort().get(key))) {
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
                return taskResultDao.findAll(specification, pageable).getContent();
            } else if (null != sort && (null == page || null == size)) {
                //无分页有排序
                return taskResultDao.findAll(specification, sort);
            } else if (null == sort && null != page && null != size) {
                //有分页无排序
                pageable = PageRequest.of(page, size);
                return taskResultDao.findAll(specification, pageable).getContent();
            } else {
                //无分页无排序
                return taskResultDao.findAll(specification);
            }
        } else {
            //无分页无排序
            return taskResultDao.findAll(specification);
        }
    }

    /**
     * 可选条件查询
     * @param taskResultParams
     * @return
     */
    @Override
    public List<TaskResult> findByOptional(TaskResultParams taskResultParams) {
        //查询条件
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != taskResultParams.getResultId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.RESULT_ID), taskResultParams.getResultId()));
            }
            if (null != taskResultParams.getUserId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.USER_ID), taskResultParams.getUserId()));
            }
            if (StringUtils.isNotBlank(taskResultParams.getUserGuid())) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.USER_GUID), taskResultParams.getUserGuid()));
            }
            if (null != taskResultParams.getProjectId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.PROJECT_ID), taskResultParams.getProjectId()));
            }
            if (null != taskResultParams.getTaskId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.TASK_ID), taskResultParams.getTaskId()));
            }
            if (null != taskResultParams.getIndex()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.INDEX), taskResultParams.getIndex()));
            }
            if (null != taskResultParams.getAmount()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.AMOUNT), taskResultParams.getAmount()));
            }
            if (null != taskResultParams.getType()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.TYPE), taskResultParams.getType()));
            }
            if (null != taskResultParams.getTaskTime_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(TaskResult_.TASK_TIME), taskResultParams.getTaskTime_start()));
            }
            if (null != taskResultParams.getTaskTime_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(TaskResult_.TASK_TIME), taskResultParams.getTaskTime_end()));
            }
            Predicate predicate = criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.where(predicate).distinct(true);
            return null;
        };
        //分页和排序
        Pageable pageable;
        if (null != taskResultParams.getPageAndSortParams()) {
            //分页
            Integer page = null;
            Integer size = null;
            if (null != taskResultParams.getPageAndSortParams().getPage()) {
                page = taskResultParams.getPageAndSortParams().getPage();
            }
            if (null != taskResultParams.getPageAndSortParams().getSize()) {
                size = taskResultParams.getPageAndSortParams().getSize();
            }
            //排序
            Sort sort = null;
            if (null != taskResultParams.getPageAndSortParams().getSort() && taskResultParams.getPageAndSortParams().getSort().size() > 0) {
                List<Sort.Order> orderList = new ArrayList<>();
                for (String key : taskResultParams.getPageAndSortParams().getSort().keySet()) {
                    if (ConstantEnum.SORT_ASC.getStringCode().equals(taskResultParams.getPageAndSortParams().getSort().get(key))) {
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
                return taskResultDao.findAll(specification, pageable).getContent();
            } else if (null != sort && (null == page || null == size)) {
                //无分页有排序
                return taskResultDao.findAll(specification, sort);
            } else if (null == sort && null != page && null != size) {
                //有分页无排序
                pageable = PageRequest.of(page, size);
                return taskResultDao.findAll(specification, pageable).getContent();
            } else {
                //无分页无排序
                return taskResultDao.findAll(specification);
            }
        } else {
            //无分页无排序
            return taskResultDao.findAll(specification);
        }
    }

    /**
     * 统计
     * @return
     */
    @Override
    public Long count() {
        return taskResultDao.count();
    }

    /**
     * 条件统计
     * @param taskResultParams
     * @return
     */
    @Override
    public Long countByParams(TaskResultParams taskResultParams) {
        //查询条件
        Specification specification = specificationHandler(taskResultParams);
        return taskResultDao.count(specification);
    }

    /**
     * 通用条件处理器
     * @param taskResultParams
     * @return
     */
    private Specification specificationHandler(TaskResultParams taskResultParams) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != taskResultParams.getResultId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.RESULT_ID), taskResultParams.getResultId()));
            }
            if (null != taskResultParams.getUserId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.USER_ID), taskResultParams.getUserId()));
            }
            if (StringUtils.isNotBlank(taskResultParams.getUserGuid())) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.USER_GUID), taskResultParams.getUserGuid()));
            }
            if (null != taskResultParams.getProjectId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.PROJECT_ID), taskResultParams.getProjectId()));
            }
            if (null != taskResultParams.getTaskId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.TASK_ID), taskResultParams.getTaskId()));
            }
            if (null != taskResultParams.getIndex()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.INDEX), taskResultParams.getIndex()));
            }
            if (null != taskResultParams.getAmount()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.AMOUNT), taskResultParams.getAmount()));
            }
            if (null != taskResultParams.getType()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskResult_.TYPE), taskResultParams.getType()));
            }
            if (null != taskResultParams.getTaskTime_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(TaskResult_.TASK_TIME), taskResultParams.getTaskTime_start()));
            }
            if (null != taskResultParams.getTaskTime_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(TaskResult_.TASK_TIME), taskResultParams.getTaskTime_end()));
            }
            Predicate predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.where(predicate).distinct(true);
            return null;
        };
        return specification;
    }
}
