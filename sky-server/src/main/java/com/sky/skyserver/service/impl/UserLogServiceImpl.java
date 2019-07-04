package com.sky.skyserver.service.impl;

import com.sky.skyentity.bean.UserLogParams;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyserver.common.ConstantEnum;
import com.sky.skyserver.dao.UserLogDao;
import com.sky.skyentity.entity.UserLog;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyentity.model.UserLog_;
import com.sky.skyserver.service.inter.UserLogService;
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

import static com.sky.skyserver.common.ConstantEnum.LOG_MEMO_USER_LOGIN;

@Service
@Transactional
public class UserLogServiceImpl implements UserLogService {
    private Logger logger = LoggerFactory.getLogger(UserLogServiceImpl.class);
    @Autowired
    UserLogDao userLogDao;

    /**
     * 新增日志
     * @param userLog
     */
    @Override
    public void save(UserLog userLog) throws BaseException {
        if (StringUtils.isBlank(userLog.getMemo()))
            throw new BaseException(BaseExceptionEnum.LOG_MEMO_IS_BLANK);
//        if (userLog.getMemo().equals(LOG_MEMO_USER_LOGIN.getStringCode()))
//            userLog.setMemo(LOG_MEMO_USER_LOGIN.getStringCode());
        userLog.setCreatedAt(new Date());//mysql升级至5.6.5+之后，该行代码可删除
        userLogDao.save(userLog);
    }

    /**
     * 按条件AND查询用户日志数据
     * @param userLogParams
     * @return
     */
    @Override
    public List<UserLog> findByParams(UserLogParams userLogParams) {
        //查询条件
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != userLogParams.getLogId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserLog_.LOG_ID), userLogParams.getLogId()));
            }
            if (null != userLogParams.getRelayId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserLog_.RELAY_ID), userLogParams.getRelayId()));
            }
            if (null != userLogParams.getUserId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserLog_.USER_ID), userLogParams.getUserId()));
            }
            if (null != userLogParams.getMemo()) {
                predicateList.add(criteriaBuilder
                        .like(root.get(UserLog_.MEMO), "%" + userLogParams.getMemo() + "%"));
            }
            if (StringUtils.isNotBlank(userLogParams.getIp())) {
                predicateList.add(criteriaBuilder
                        .like(root.get(UserLog_.IP), "%" + userLogParams.getIp() + "%"));
            }
            if (null != userLogParams.getCreatedAt_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(UserLog_.CREATED_AT), userLogParams.getCreatedAt_start()));
            }
            if (null != userLogParams.getCreatedAt_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(UserLog_.CREATED_AT), userLogParams.getCreatedAt_end()));
            }
            Predicate predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.where(predicate).distinct(true);
            return null;
        };
        //分页和排序
        Pageable pageable;
        if (null != userLogParams.getPageAndSortParams()) {
            //分页
            Integer page = null;
            Integer size = null;
            if (null != userLogParams.getPageAndSortParams().getPage()) {
                page = userLogParams.getPageAndSortParams().getPage();
            }
            if (null != userLogParams.getPageAndSortParams().getSize()) {
                size = userLogParams.getPageAndSortParams().getSize();
            }
            //排序
            Sort sort = null;
            if (null != userLogParams.getPageAndSortParams().getSort() && userLogParams.getPageAndSortParams().getSort().size() > 0) {
                List<Sort.Order> orderList = new ArrayList<>();
                for (String key : userLogParams.getPageAndSortParams().getSort().keySet()) {
                    if (ConstantEnum.SORT_ASC.getStringCode().equals(userLogParams.getPageAndSortParams().getSort().get(key))) {
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
                return userLogDao.findAll(specification, pageable).getContent();
            } else if (null != sort && (null == page || null == size)) {
                //无分页有排序
                return userLogDao.findAll(specification, sort);
            } else if (null == sort && null != page && null != size) {
                //有分页无排序
                pageable = PageRequest.of(page, size);
                return userLogDao.findAll(specification, pageable).getContent();
            } else {
                //无分页无排序
                return userLogDao.findAll(specification);
            }
        } else {
            //无分页无排序
            return userLogDao.findAll(specification);
        }
    }
}
