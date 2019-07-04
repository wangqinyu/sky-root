package com.sky.skyserver.service.impl;

import com.sky.skyentity.bean.ProjectInfoParams;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyserver.common.ConstantEnum;
import com.sky.skyserver.dao.ProjectInfoDao;
import com.sky.skyserver.dao.TaskInfoDao;
import com.sky.skyentity.entity.ProjectInfo;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyentity.model.ProjectInfo_;
import com.sky.skyserver.service.inter.ProjectInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;

import static com.sky.skyserver.common.BaseExceptionEnum.*;

@Service
@Transactional
public class ProjectInfoServiceImpl implements ProjectInfoService {
    private Logger logger = LoggerFactory.getLogger(ProjectInfoServiceImpl.class);
    @Autowired
    ProjectInfoDao projectInfoDao;
    @Autowired
    TaskInfoDao taskInfoDao;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 判断是否已存在项目信息（判断参数：name）
     *
     * @param name
     * @return
     * @throws BaseException
     */
    public boolean isExistedProjectInfo(String name) throws BaseException {
        if (StringUtils.isBlank(name))
            throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
        ProjectInfo params = new ProjectInfo();
        params.setName(name);
        Example<ProjectInfo> example = Example.of(params);
        Optional<ProjectInfo> byOptional = projectInfoDao.findOne(example);
        if (byOptional.isPresent()) {
            return true;
        }
        return false;
    }

    /**
     * 保存项目数据
     *
     * @param projectInfo
     * @throws BaseException
     */
    @Override
    public void save(ProjectInfo projectInfo) throws BaseException {
        //项目名校验(必填)
        if (StringUtils.isBlank(projectInfo.getName()))
            throw new BaseException(PROJECT_NAME_IS_BLANK);
        //项目总数量校验(必填)
        if (null == projectInfo.getAmount())
            throw new BaseException(PROJECT_AMOUNT_IS_BLANK);
        //初始化剩余量
        projectInfo.setLeft(projectInfo.getAmount());
        //二维码类型/Url生成器/上报标记校验(必填)
        if (null == projectInfo.getUrlType() || StringUtils.isBlank(projectInfo.getUrlGen()) || null == projectInfo.getPostFlag())
            throw new BaseException(VALUE_NOT_COMPLETED);
        //查询该名称是否注册过
        if (isExistedProjectInfo(projectInfo.getName()))
            throw new BaseException(BaseExceptionEnum.PROJECT_NAME_EXISTED);
        //存储
        projectInfo.setCreatedAt(new Date());//数据库版本提升5.6.5+后去掉，默认设置为timestamps
        projectInfoDao.save(projectInfo);
    }

    /**
     * 更新项目数据
     *
     * @param projectInfo
     * @throws BaseException
     */
    @Override
    public void update(ProjectInfo projectInfo) throws BaseException {
        //通过id查询是否存在此项目信息
        if (null == projectInfo.getProjectId())
            throw new BaseException(VALUE_NOT_COMPLETED);
        Optional<ProjectInfo> searchProjectInfo = projectInfoDao.findById(projectInfo.getProjectId());
        if (!searchProjectInfo.isPresent())
            throw new BaseException(PROJECT_NOT_EXISTED);
        ProjectInfo updateProjectInfo = searchProjectInfo.get();
        if (StringUtils.isNotBlank(projectInfo.getName())) updateProjectInfo.setName(projectInfo.getName());
        if (StringUtils.isNotBlank(projectInfo.getDesc())) updateProjectInfo.setDesc(projectInfo.getDesc());
        if (StringUtils.isNotBlank(projectInfo.getContent())) updateProjectInfo.setContent(projectInfo.getContent());
        if (null != projectInfo.getAmount()) updateProjectInfo.setAmount(projectInfo.getAmount());
        if (null != projectInfo.getLeft()) updateProjectInfo.setLeft(projectInfo.getLeft());
        if (null != projectInfo.getMinAmount()) updateProjectInfo.setMinAmount(projectInfo.getMinAmount());
        if (null != projectInfo.getMaxAmount()) updateProjectInfo.setMaxAmount(projectInfo.getMaxAmount());
        if (null != projectInfo.getStartTime()) updateProjectInfo.setStartTime(projectInfo.getStartTime());
        if (null != projectInfo.getEndTime()) updateProjectInfo.setEndTime(projectInfo.getEndTime());
        if (null != projectInfo.getUrlType()) updateProjectInfo.setUrlType(projectInfo.getUrlType());
        if (null != projectInfo.getPostFlag()) updateProjectInfo.setPostFlag(projectInfo.getPostFlag());
        if (StringUtils.isNotBlank(projectInfo.getUrlGen())) updateProjectInfo.setUrlGen(projectInfo.getUrlGen());
        //唯一性校验
        if (StringUtils.isNotBlank(projectInfo.getName())) {
            if (isExistedProjectInfo(projectInfo.getName()))
                throw new BaseException(BaseExceptionEnum.PROJECT_NAME_EXISTED);
        }
        //更新
        projectInfoDao.saveAndFlush(updateProjectInfo);
    }

    /**
     * 查询全部项目数据
     *
     * @return
     */
    @Override
    public List<ProjectInfo> findAll() {
        return projectInfoDao.findAll();
    }

    /**
     * 按条件查询项目数据
     *
     * @param projectInfoParams
     * @return
     */
    @Override
    public List<ProjectInfo> findByParams(ProjectInfoParams projectInfoParams) {
        //查询条件
        Specification specification = specificationHandler(projectInfoParams);
        //分页和排序
        Pageable pageable;
        if (null != projectInfoParams.getPageAndSortParams()) {
            //分页
            Integer page = null;
            Integer size = null;
            if (null != projectInfoParams.getPageAndSortParams().getPage()) {
                page = projectInfoParams.getPageAndSortParams().getPage();
            }
            if (null != projectInfoParams.getPageAndSortParams().getSize()) {
                size = projectInfoParams.getPageAndSortParams().getSize();
            }
            //排序
            Sort sort = null;
            if (null != projectInfoParams.getPageAndSortParams().getSort() && projectInfoParams.getPageAndSortParams().getSort().size() > 0) {
                List<Sort.Order> orderList = new ArrayList<>();
                for (String key : projectInfoParams.getPageAndSortParams().getSort().keySet()) {
                    if (ConstantEnum.SORT_ASC.getStringCode().equals(projectInfoParams.getPageAndSortParams().getSort().get(key))) {
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
                return projectInfoDao.findAll(specification, pageable).getContent();
            } else if (null != sort && (null == page || null == size)) {
                //无分页有排序
                return projectInfoDao.findAll(specification, sort);
            } else if (null == sort && null != page && null != size) {
                //有分页无排序
                pageable = PageRequest.of(page, size);
                return projectInfoDao.findAll(specification, pageable).getContent();
            } else {
                //无分页无排序
                return projectInfoDao.findAll(specification);
            }
        } else {
            //无分页无排序
            return projectInfoDao.findAll(specification);
        }
    }

    /**
     * 可选条件查询
     * (条件全空时不做查询)
     *
     * @param projectInfoParams
     * @return
     */
    @Override
    public List<ProjectInfo> findByOptional(ProjectInfoParams projectInfoParams) {
        //查询条件
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != projectInfoParams.getProjectId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(ProjectInfo_.PROJECT_ID), projectInfoParams.getProjectId()));
            }
            if (null != projectInfoParams.getStatus()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(ProjectInfo_.STATUS), projectInfoParams.getStatus()));
            }
            if (StringUtils.isNotBlank(projectInfoParams.getName())) {
                predicateList.add(criteriaBuilder
                        .like(root.get(ProjectInfo_.NAME), "%" + projectInfoParams.getName() + "%"));
            }
            if (StringUtils.isNotBlank(projectInfoParams.getDesc())) {
                predicateList.add(criteriaBuilder
                        .like(root.get(ProjectInfo_.DESC), "%" + projectInfoParams.getDesc() + "%"));
            }
            if (null != projectInfoParams.getAmount()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(ProjectInfo_.AMOUNT), projectInfoParams.getAmount()));
            }
            if (null != projectInfoParams.getLeft_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(ProjectInfo_.LEFT), projectInfoParams.getLeft_start()));
            }
            if (null != projectInfoParams.getLeft_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(ProjectInfo_.LEFT), projectInfoParams.getLeft_end()));
            }
            if (null != projectInfoParams.getStartTime_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(ProjectInfo_.START_TIME), projectInfoParams.getStartTime_start()));
            }
            if (null != projectInfoParams.getStartTime_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(ProjectInfo_.START_TIME), projectInfoParams.getStartTime_end()));
            }
            if (null != projectInfoParams.getEndTime_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(ProjectInfo_.END_TIME), projectInfoParams.getEndTime_start()));
            }
            if (null != projectInfoParams.getEndTime_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(ProjectInfo_.END_TIME), projectInfoParams.getEndTime_end()));
            }
            if (null != projectInfoParams.getUrlType()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(ProjectInfo_.URL_TYPE), projectInfoParams.getUrlType()));
            }
            if (StringUtils.isNotBlank(projectInfoParams.getUrlGen())) {
                predicateList.add(criteriaBuilder
                        .like(root.get(ProjectInfo_.URL_GEN), projectInfoParams.getUrlGen()));
            }
            if (null != projectInfoParams.getPostFlag()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(ProjectInfo_.POST_FLAG), projectInfoParams.getPostFlag()));
            }
            if (null != projectInfoParams.getCreatedAt_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(ProjectInfo_.CREATED_AT), projectInfoParams.getCreatedAt_start()));
            }
            if (null != projectInfoParams.getCreatedAt_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(ProjectInfo_.CREATED_AT), projectInfoParams.getCreatedAt_end()));
            }
            Predicate predicate = criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.where(predicate).distinct(true);
            return null;
        };
        //分页和排序
        Pageable pageable;
        if (null != projectInfoParams.getPageAndSortParams()) {
            //分页
            Integer page = null;
            Integer size = null;
            if (null != projectInfoParams.getPageAndSortParams().getPage()) {
                page = projectInfoParams.getPageAndSortParams().getPage();
            }
            if (null != projectInfoParams.getPageAndSortParams().getSize()) {
                size = projectInfoParams.getPageAndSortParams().getSize();
            }
            //排序
            Sort sort = null;
            if (null != projectInfoParams.getPageAndSortParams().getSort() && projectInfoParams.getPageAndSortParams().getSort().size() > 0) {
                List<Sort.Order> orderList = new ArrayList<>();
                for (String key : projectInfoParams.getPageAndSortParams().getSort().keySet()) {
                    if (ConstantEnum.SORT_ASC.getStringCode().equals(projectInfoParams.getPageAndSortParams().getSort().get(key))) {
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
                return projectInfoDao.findAll(specification, pageable).getContent();
            } else if (null != sort && (null == page || null == size)) {
                //无分页有排序
                return projectInfoDao.findAll(specification, sort);
            } else if (null == sort && null != page && null != size) {
                //有分页无排序
                pageable = PageRequest.of(page, size);
                return projectInfoDao.findAll(specification, pageable).getContent();
            } else {
                //无分页无排序
                return projectInfoDao.findAll(specification);
            }
        } else {
            //无分页无排序
            return projectInfoDao.findAll(specification);
        }
    }

    /**
     * 删除项目数据
     *
     * @param projectId
     * @throws BaseException
     */
    @Override
    public void delete(Long projectId) throws BaseException {
        //通过id查询是否存在此项目信息
        if (null == projectId) {
            throw new BaseException(VALUE_NOT_COMPLETED);
        } else {
            Optional<ProjectInfo> searchProjectInfo = projectInfoDao.findById(projectId);
            if (!searchProjectInfo.isPresent()) {
                throw new BaseException(USER_NOT_EXISTED);
            } else {
                projectInfoDao.deleteById(projectId);
            }
        }
    }

    /**
     * 统计项目量
     *
     * @return
     */
    @Override
    public Long count() {
        return projectInfoDao.count();
    }

    /**
     * 根据条件统计项目量
     *
     * @param projectInfoParams
     * @return
     */
    @Override
    public Long countByParams(ProjectInfoParams projectInfoParams) {
        //查询条件
        Specification specification = specificationHandler(projectInfoParams);
        return projectInfoDao.count(specification);
    }

    /**
     * 获取项目信息
     *
     * @param projectId
     * @return
     * @throws BaseException
     */
    @Override
    public ProjectInfo getProjectInfo(Long projectId) throws BaseException {
        if (null == projectId) throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
        Optional<ProjectInfo> projectInfoOptional = projectInfoDao.findById(projectId);
        if (!projectInfoOptional.isPresent()) throw new BaseException(BaseExceptionEnum.PROJECT_NOT_EXISTED);
        return projectInfoOptional.get();
    }

    /**
     * 更改项目状态
     * @param projectInfoParams
     * @throws BaseException
     */
    @Override
    public void updateStatus(ProjectInfoParams projectInfoParams) throws BaseException {
        //判断项目信息是否存在
        Optional<ProjectInfo> projectInfoOptional = projectInfoDao.findById(projectInfoParams.getProjectId());
        ProjectInfo projectInfo = projectInfoOptional.get();
        if (!projectInfoOptional.isPresent())
            throw new BaseException(BaseExceptionEnum.PROJECT_NOT_EXISTED);
        //更改项目状态
        projectInfo.setStatus(projectInfoParams.getStatus());
        projectInfoDao.saveAndFlush(projectInfo);
    }

    /**
     * 通用条件处理器（创建）
     * @param projectInfoParams
     * @return
     */
    private Specification specificationHandler(ProjectInfoParams projectInfoParams) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != projectInfoParams.getProjectId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(ProjectInfo_.PROJECT_ID), projectInfoParams.getProjectId()));
            }
            if (null != projectInfoParams.getStatus()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(ProjectInfo_.STATUS), projectInfoParams.getStatus()));
            }
            if (StringUtils.isNotBlank(projectInfoParams.getName())) {
                predicateList.add(criteriaBuilder
                        .like(root.get(ProjectInfo_.NAME), "%" + projectInfoParams.getName() + "%"));
            }
            if (StringUtils.isNotBlank(projectInfoParams.getDesc())) {
                predicateList.add(criteriaBuilder
                        .like(root.get(ProjectInfo_.DESC), "%" + projectInfoParams.getDesc() + "%"));
            }
            if (null != projectInfoParams.getAmount()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(ProjectInfo_.AMOUNT), projectInfoParams.getAmount()));
            }
            if (null != projectInfoParams.getLeft_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(ProjectInfo_.LEFT), projectInfoParams.getLeft_start()));
            }
            if (null != projectInfoParams.getLeft_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(ProjectInfo_.LEFT), projectInfoParams.getLeft_end()));
            }
            if (null != projectInfoParams.getStartTime_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(ProjectInfo_.START_TIME), projectInfoParams.getStartTime_start()));
            }
            if (null != projectInfoParams.getStartTime_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(ProjectInfo_.START_TIME), projectInfoParams.getStartTime_end()));
            }
            if (null != projectInfoParams.getEndTime_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(ProjectInfo_.END_TIME), projectInfoParams.getEndTime_start()));
            }
            if (null != projectInfoParams.getEndTime_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(ProjectInfo_.END_TIME), projectInfoParams.getEndTime_end()));
            }
            if (null != projectInfoParams.getUrlType()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(ProjectInfo_.URL_TYPE), projectInfoParams.getUrlType()));
            }
            if (StringUtils.isNotBlank(projectInfoParams.getUrlGen())) {
                predicateList.add(criteriaBuilder
                        .like(root.get(ProjectInfo_.URL_GEN), projectInfoParams.getUrlGen()));
            }
            if (null != projectInfoParams.getPostFlag()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(ProjectInfo_.POST_FLAG), projectInfoParams.getPostFlag()));
            }
            if (null != projectInfoParams.getCreatedAt_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(ProjectInfo_.CREATED_AT), projectInfoParams.getCreatedAt_start()));
            }
            if (null != projectInfoParams.getCreatedAt_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(ProjectInfo_.CREATED_AT), projectInfoParams.getCreatedAt_end()));
            }
            Predicate predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.where(predicate).distinct(true);
            return null;
        };
        return specification;
    }
}
