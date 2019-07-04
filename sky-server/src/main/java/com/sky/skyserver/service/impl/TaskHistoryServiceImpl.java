package com.sky.skyserver.service.impl;

import com.sky.skyentity.bean.TaskHistoryParams;
import com.sky.skyserver.common.ConstantEnum;
import com.sky.skyserver.dao.TaskHistoryDao;
import com.sky.skyentity.entity.TaskHistory;
import com.sky.skyentity.model.TaskHistory_;
import com.sky.skyserver.service.inter.TaskHistoryService;

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
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class TaskHistoryServiceImpl implements TaskHistoryService {
    private Logger logger = LoggerFactory.getLogger(TaskInfoServiceImpl.class);
    @Autowired
    TaskHistoryDao taskHistoryDao;

    /**
     * 新增任务历史数据
     * @param taskHistory
     */
    @Override
    public void save(TaskHistory taskHistory) {
        taskHistory.setCreatedAt(new Date());//Mysql数据库版本提升至5.6.5+后，可删除此代码
        taskHistoryDao.save(taskHistory);
    }

    /**
     * 按条件查询任务历史数据
     * @param taskHistoryParams
     * @return
     */
    @Override
    public List<TaskHistory> findByParams(TaskHistoryParams taskHistoryParams) {
        //查询条件
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != taskHistoryParams.getHistoryId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskHistory_.HISTORY_ID), taskHistoryParams.getHistoryId()));
            }
            if (null != taskHistoryParams.getUserId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskHistory_.USER_ID), taskHistoryParams.getUserId()));
            }
            if (null != taskHistoryParams.getTaskId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskHistory_.TASK_ID), taskHistoryParams.getTaskId()));
            }
            if (null != taskHistoryParams.getProjectId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskHistory_.PROJECT_ID), taskHistoryParams.getProjectId()));
            }
            if (null != taskHistoryParams.getAmount()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskHistory_.AMOUNT), taskHistoryParams.getAmount()));
            }
            if (null != taskHistoryParams.getCreatedAt_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(TaskHistory_.CREATED_AT), taskHistoryParams.getCreatedAt_start()));
            }
            if (null != taskHistoryParams.getCreatedAt_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(TaskHistory_.CREATED_AT), taskHistoryParams.getCreatedAt_end()));
            }
            Predicate predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.where(predicate).distinct(true);
            return null;
        };
        //分页和排序
        Pageable pageable;
        if (null != taskHistoryParams.getPageAndSortParams()) {
            //分页
            Integer page = null;
            Integer size = null;
            if (null != taskHistoryParams.getPageAndSortParams().getPage()) {
                page = taskHistoryParams.getPageAndSortParams().getPage();
            }
            if (null != taskHistoryParams.getPageAndSortParams().getSize()) {
                size = taskHistoryParams.getPageAndSortParams().getSize();
            }
            //排序
            Sort sort = null;
            if (null != taskHistoryParams.getPageAndSortParams().getSort() && taskHistoryParams.getPageAndSortParams().getSort().size() > 0) {
                List<Sort.Order> orderList = new ArrayList<>();
                for (String key : taskHistoryParams.getPageAndSortParams().getSort().keySet()) {
                    if (ConstantEnum.SORT_ASC.getStringCode().equals(taskHistoryParams.getPageAndSortParams().getSort().get(key))) {
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
                return taskHistoryDao.findAll(specification, pageable).getContent();
            } else if (null != sort && (null == page || null == size)) {
                //无分页有排序
                return taskHistoryDao.findAll(specification, sort);
            } else if (null == sort && null != page && null != size) {
                //有分页无排序
                pageable = PageRequest.of(page, size);
                return taskHistoryDao.findAll(specification, pageable).getContent();
            } else {
                //无分页无排序
                return taskHistoryDao.findAll(specification);
            }
        } else {
            //无分页无排序
            return taskHistoryDao.findAll(specification);
        }
    }
}
