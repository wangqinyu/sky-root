package com.sky.skyserver.service.impl;

import com.sky.skyentity.bean.TaskStepParams;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyserver.common.ConstantEnum;
import com.sky.skyserver.dao.TaskStepDao;
import com.sky.skyentity.entity.TaskStep;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyentity.model.TaskStep_;
import com.sky.skyserver.service.inter.TaskStepService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.sky.skyserver.common.BaseExceptionEnum.TASK_STEP_NOT_EXISTED;
import static com.sky.skyserver.common.BaseExceptionEnum.VALUE_NOT_COMPLETED;

@Service
@Transactional
public class TaskStepServiceImpl implements TaskStepService {
    private Logger logger = LoggerFactory.getLogger(TaskStepServiceImpl.class);

    @Autowired
    TaskStepDao taskStepDao;

    /**
     * 新增标签数据
     * @param taskStep
     * @throws BaseException
     */
    @Override
    public void save(TaskStep taskStep) throws BaseException {
        if (null != taskStep.getStepId())
            throw new BaseException(BaseExceptionEnum.TASK_STEP_EXISTED);
        taskStep.setCreatedAt(new Date());//Mysql数据库版本提升至5.6.5+后，可删除此代码
        taskStepDao.save(taskStep);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<TaskStep> findAll() {
        return taskStepDao.findAll();
    }

    /**
     * 条件查询
     * @param taskStepParams
     * @return
     */
    @Override
    public List<TaskStep> findByParams(TaskStepParams taskStepParams) {
        //查询条件
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != taskStepParams.getStepId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskStep_.STEP_ID), taskStepParams.getStepId()));
            }
            if (null != taskStepParams.getProjectId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskStep_.PROJECT_ID), taskStepParams.getProjectId()));
            }
            if (null != taskStepParams.getIndex()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskStep_.INDEX), taskStepParams.getIndex()));
            }
            if (null != taskStepParams.getName()) {
                predicateList.add(criteriaBuilder
                        .like(root.get(TaskStep_.NAME), "%" + taskStepParams.getName() + "%"));
            }
            if (null != taskStepParams.getAlias()) {
                predicateList.add(criteriaBuilder
                        .like(root.get(TaskStep_.ALIAS), "%" + taskStepParams.getAlias() + "%"));
            }
            if (null != taskStepParams.getExtra()) {
                predicateList.add(criteriaBuilder
                        .like(root.get(TaskStep_.EXTRA), "%" + taskStepParams.getExtra() + "%"));
            }
            Predicate predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.where(predicate).distinct(true);
            return null;
        };
        //分页和排序
        Pageable pageable;
        if (null != taskStepParams.getPageAndSortParams()) {
            //分页
            Integer page = null;
            Integer size = null;
            if (null != taskStepParams.getPageAndSortParams().getPage()) {
                page = taskStepParams.getPageAndSortParams().getPage();
            }
            if (null != taskStepParams.getPageAndSortParams().getSize()) {
                size = taskStepParams.getPageAndSortParams().getSize();
            }
            //排序
            Sort sort = null;
            if (null != taskStepParams.getPageAndSortParams().getSort() && taskStepParams.getPageAndSortParams().getSort().size() > 0) {
                List<Sort.Order> orderList = new ArrayList<>();
                for (String key : taskStepParams.getPageAndSortParams().getSort().keySet()) {
                    if (ConstantEnum.SORT_ASC.getStringCode().equals(taskStepParams.getPageAndSortParams().getSort().get(key))) {
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
                return taskStepDao.findAll(specification, pageable).getContent();
            } else if (null != sort && (null == page || null == size)) {
                //无分页有排序
                return taskStepDao.findAll(specification, sort);
            } else if (null == sort && null != page && null != size) {
                //有分页无排序
                pageable = PageRequest.of(page, size);
                return taskStepDao.findAll(specification, pageable).getContent();
            } else {
                //无分页无排序
                return taskStepDao.findAll(specification);
            }
        } else {
            //无分页无排序
            return taskStepDao.findAll(specification);
        }
    }

    /**
     * 更新标签信息
     * @param taskStep
     * @throws BaseException
     */
    @Override
    public void update(TaskStep taskStep) throws BaseException {
        //通过id查询是否存在此信息
        if (null == taskStep.getStepId())
            throw new BaseException(VALUE_NOT_COMPLETED);
        Optional<TaskStep> searchUserInfo = taskStepDao.findById(taskStep.getStepId());
        if (!searchUserInfo.isPresent())
            throw new BaseException(TASK_STEP_NOT_EXISTED);
        TaskStep taskStepUpdate = searchUserInfo.get();
        if (StringUtils.isNotBlank(taskStep.getName())) taskStepUpdate.setName(taskStep.getName());
        if (StringUtils.isNotBlank(taskStep.getAlias())) taskStepUpdate.setAlias(taskStep.getAlias());
        if (StringUtils.isNotBlank(taskStep.getExtra())) taskStepUpdate.setExtra(taskStep.getExtra());
        if (null != taskStep.getProjectId()) taskStepUpdate.setProjectId(taskStep.getProjectId());
        if (null != taskStep.getIndex()) taskStepUpdate.setIndex(taskStep.getIndex());
        //更新
        taskStepDao.saveAndFlush(taskStepUpdate);
    }

    /**
     * 可选条件查询
     * @param taskStepParams
     * @return
     */
    @Override
    public List<TaskStep> findByOptional(TaskStepParams taskStepParams) {
        //查询条件
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != taskStepParams.getStepId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskStep_.STEP_ID), taskStepParams.getStepId()));
            }
            if (null != taskStepParams.getProjectId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskStep_.PROJECT_ID), taskStepParams.getProjectId()));
            }
            if (null != taskStepParams.getIndex()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskStep_.INDEX), taskStepParams.getIndex()));
            }
            if (null != taskStepParams.getName()) {
                predicateList.add(criteriaBuilder
                        .like(root.get(TaskStep_.NAME), "%" + taskStepParams.getName() + "%"));
            }
            if (null != taskStepParams.getAlias()) {
                predicateList.add(criteriaBuilder
                        .like(root.get(TaskStep_.ALIAS), "%" + taskStepParams.getAlias() + "%"));
            }
            if (null != taskStepParams.getExtra()) {
                predicateList.add(criteriaBuilder
                        .like(root.get(TaskStep_.EXTRA), "%" + taskStepParams.getExtra() + "%"));
            }
            Predicate predicate = criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.where(predicate).distinct(true);
            return null;
        };
        //分页和排序
        Pageable pageable;
        if (null != taskStepParams.getPageAndSortParams()) {
            //分页
            Integer page = null;
            Integer size = null;
            if (null != taskStepParams.getPageAndSortParams().getPage()) {
                page = taskStepParams.getPageAndSortParams().getPage();
            }
            if (null != taskStepParams.getPageAndSortParams().getSize()) {
                size = taskStepParams.getPageAndSortParams().getSize();
            }
            //排序
            Sort sort = null;
            if (null != taskStepParams.getPageAndSortParams().getSort() && taskStepParams.getPageAndSortParams().getSort().size() > 0) {
                List<Sort.Order> orderList = new ArrayList<>();
                for (String key : taskStepParams.getPageAndSortParams().getSort().keySet()) {
                    if (ConstantEnum.SORT_ASC.getStringCode().equals(taskStepParams.getPageAndSortParams().getSort().get(key))) {
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
                return taskStepDao.findAll(specification, pageable).getContent();
            } else if (null != sort && (null == page || null == size)) {
                //无分页有排序
                return taskStepDao.findAll(specification, sort);
            } else if (null == sort && null != page && null != size) {
                //有分页无排序
                pageable = PageRequest.of(page, size);
                return taskStepDao.findAll(specification, pageable).getContent();
            } else {
                //无分页无排序
                return taskStepDao.findAll(specification);
            }
        } else {
            //无分页无排序
            return taskStepDao.findAll(specification);
        }
    }

    /**
     * 删除标签信息
     * @param taskId
     * @throws BaseException
     */
    @Override
    public void delete(Long taskId) throws BaseException {
        //通过id查询是否存在此信息
        if (null == taskId) {
            throw new BaseException(VALUE_NOT_COMPLETED);
        } else {
            Optional<TaskStep> searchUserInfo = taskStepDao.findById(taskId);
            if (!searchUserInfo.isPresent()) {
                throw new BaseException(TASK_STEP_NOT_EXISTED);
            } else {
                taskStepDao.deleteById(taskId);
            }
        }
    }

    /**
     * 统计
     * @return
     */
    @Override
    public Long count() {
        return taskStepDao.count();
    }

    /**
     * 条件统计
     * @param taskStepParams
     * @return
     */
    @Override
    public Long countByParams(TaskStepParams taskStepParams) {
        //查询条件
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != taskStepParams.getStepId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskStep_.STEP_ID), taskStepParams.getStepId()));
            }
            if (null != taskStepParams.getProjectId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskStep_.PROJECT_ID), taskStepParams.getProjectId()));
            }
            if (null != taskStepParams.getIndex()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(TaskStep_.INDEX), taskStepParams.getIndex()));
            }
            if (null != taskStepParams.getName()) {
                predicateList.add(criteriaBuilder
                        .like(root.get(TaskStep_.NAME), "%" + taskStepParams.getName() + "%"));
            }
            if (null != taskStepParams.getAlias()) {
                predicateList.add(criteriaBuilder
                        .like(root.get(TaskStep_.ALIAS), "%" + taskStepParams.getAlias() + "%"));
            }
            if (null != taskStepParams.getExtra()) {
                predicateList.add(criteriaBuilder
                        .like(root.get(TaskStep_.EXTRA), "%" + taskStepParams.getExtra() + "%"));
            }
            Predicate predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.where(predicate).distinct(true);
            return null;
        };
        return taskStepDao.count(specification);
    }

    /**
     * 获取标签信息
     * @param taskId
     * @return
     * @throws BaseException
     */
    @Override
    public TaskStep getTaskStep(Long taskId) throws BaseException {
        if (null == taskId) throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
        Optional<TaskStep> userInfoOptional = taskStepDao.findById(taskId);
        if (!userInfoOptional.isPresent()) throw new BaseException(BaseExceptionEnum.TASK_STEP_NOT_EXISTED);
        return userInfoOptional.get();
    }
}
