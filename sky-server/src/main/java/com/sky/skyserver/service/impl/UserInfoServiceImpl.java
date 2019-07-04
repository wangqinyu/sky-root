package com.sky.skyserver.service.impl;

import com.sky.skyentity.bean.QRCodeParams;
import com.sky.skyentity.bean.TaskInfoParams;
import com.sky.skyentity.bean.UserInfoParams;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyserver.common.ConstantEnum;
import com.sky.skyserver.dao.ProjectInfoDao;
import com.sky.skyserver.dao.UserLogDao;
import com.sky.skyentity.entity.ProjectInfo;
import com.sky.skyentity.entity.UserLog;
import com.sky.skyentity.model.UserInfo_;
import com.sky.skyserver.service.inter.QRCodeService;
import com.sky.skyserver.service.inter.UserInfoService;
import com.sky.skyserver.dao.UserInfoDao;
import com.sky.skyentity.entity.UserInfo;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyserver.util.ValidatorUtils;
import com.sky.skyserver.util.security.CryptionUtils;
import com.sky.skyserver.util.security.PasswordKeys;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;

import static com.sky.skyserver.common.BaseExceptionEnum.*;
import static com.sky.skyserver.common.ConstantEnum.*;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    private Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    UserLogDao userLogDao;
    @Autowired
    ProjectInfoDao projectInfoDao;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    QRCodeService qrCodeService;

    /**
     * 判断是否已存在用户信息（判断参数：name、phone、mail）
     *
     * @param name
     * @param phone
     * @param mail
     * @return
     * @throws BaseException
     */
    public boolean isExistedUserInfo(String name, String phone, String mail) throws BaseException {
        UserInfoParams params = new UserInfoParams();
        if (StringUtils.isNotBlank(name)) params.setName(name);
        if (StringUtils.isNotBlank(phone)) params.setPhone(phone);
        if (StringUtils.isNotBlank(mail)) params.setMail(mail);
        List<UserInfo> byOptional = findByOptional(params);
        if (null != byOptional && byOptional.size() > 0) {
            if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(byOptional.get(0).getName()) && byOptional.get(0).getName().equals(name))
                throw new BaseException(USERNAME_EXISTED);
            if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(byOptional.get(0).getPhone()) && byOptional.get(0).getPhone().equals(phone))
                throw new BaseException(PHONE_EXISTED);
            if (StringUtils.isNotBlank(mail) && StringUtils.isNotBlank(byOptional.get(0).getMail()) && byOptional.get(0).getMail().equals(mail))
                throw new BaseException(MAIL_EXISTED);
        }
        return false;
    }

    /**
     * 保存用户数据
     *
     * @param userInfo
     * @throws BaseException
     */
    @Override
    public void save(UserInfo userInfo,String ip) throws BaseException {
        //邮箱校验(选填)
        if (StringUtils.isNotBlank(userInfo.getMail()) && !ValidatorUtils.isEmail(userInfo.getMail())) {
            throw new BaseException(MAIL_INCORRECT);
        }
        //用户名校验(必填)
        if (StringUtils.isBlank(userInfo.getName())) {
            throw new BaseException(USERNAME_IS_BLANK);
        } else {
            if (!ValidatorUtils.isUsername(userInfo.getName())) {
                throw new BaseException(USERNAME_INCORRECT);
            }
        }
        //密码创建(必填)
        passwordHandler(userInfo, userInfo.getPassword());
        //手机号码校验(必填)
        if (StringUtils.isBlank(userInfo.getPhone())) {
            throw new BaseException(PHONE_IS_BLANK);
        } else {
            if (!ValidatorUtils.isMobile(userInfo.getPhone())) {
                throw new BaseException(PHONE_INCORRECT);
            }
        }
        //查询该手机号/账号/邮箱是否注册过
        if (!isExistedUserInfo(userInfo.getName(), userInfo.getPhone(), userInfo.getMail())) {
            userInfo.setCreatedAt(new Date());//数据库版本提升5.6.5+后去掉，默认设置为timestamps
            userInfo.setUserGuid(UUID.randomUUID().toString());
            UserInfo save = userInfoDao.save(userInfo);
            //用户日志记录
            UserLog userLog = new UserLog();
            userLog.setUserId(save.getUserId());
            userLog.setMemo(LOG_MEMO_USER_REGISTER.getStringCode());
            userLog.setIp(ip);
            userLog.setCreatedAt(new Date());//mysql升级至5.6.5+之后，该行代码可删除
            userLogDao.save(userLog);
        }
    }

    /**
     * 更新用户数据
     *
     * @param userInfo
     * @throws BaseException
     */
    @Override
    public void update(UserInfo userInfo,String ip) throws BaseException {
        //通过id查询是否存在此用户信息
        if (null == userInfo.getUserId())
            throw new BaseException(VALUE_NOT_COMPLETED);
        Optional<UserInfo> searchUserInfo = userInfoDao.findById(userInfo.getUserId());
        if (!searchUserInfo.isPresent())
            throw new BaseException(USER_NOT_EXISTED);
        UserInfo userInfoUpdate = searchUserInfo.get();
        if (StringUtils.isNotBlank(userInfo.getRealName())) userInfoUpdate.setRealName(userInfo.getRealName());
        if (StringUtils.isNotBlank(userInfo.getPhone())) userInfoUpdate.setPhone(userInfo.getPhone());
        if (StringUtils.isNotBlank(userInfo.getMail())) userInfoUpdate.setMail(userInfo.getMail());
        if (null != userInfo.getType()) userInfoUpdate.setType(userInfo.getType());
        if (null != userInfo.getUserAuth()) userInfoUpdate.setUserAuth(userInfo.getUserAuth());
        if (null != userInfo.getUserPid()) userInfoUpdate.setUserPid(userInfo.getUserPid());
        if (null != userInfo.getStatus()) userInfoUpdate.setStatus(userInfo.getStatus());//默认值为0
        if (null != userInfo.getScore()) userInfoUpdate.setScore(userInfo.getScore());//默认值为0
        //唯一性校验
        if (StringUtils.isNotBlank(userInfo.getName()) || StringUtils.isNotBlank(userInfo.getPhone()) || StringUtils.isNotBlank(userInfo.getMail())){
            isExistedUserInfo(userInfo.getName(),userInfo.getPhone(),userInfo.getMail());
        }
        //更新
        UserInfo saveAndFlush = userInfoDao.saveAndFlush(userInfoUpdate);
        //用户日志记录
        UserLog userLog = new UserLog();
        userLog.setUserId(saveAndFlush.getUserId());
        userLog.setMemo(LOG_MEMO_USER_UPDATE.getStringCode());
        userLog.setIp(ip);
        userLog.setCreatedAt(new Date());//mysql升级至5.6.5+之后，该行代码可删除
        userLogDao.save(userLog);
    }

    /**
     * 查询全部用户数据
     *
     * @return
     */
    @Override
    public List<UserInfo> findAll() {
        return userInfoDao.findAll();
    }

    /**
     * 按条件查询用户数据
     *
     * @param userInfoParams
     * @return
     */
    @Override
    public List<UserInfo> findByParams(UserInfoParams userInfoParams) {
        //查询条件
        Specification specification = specificationHandler(userInfoParams);
        //分页和排序
        Pageable pageable;
        if (null != userInfoParams.getPageAndSortParams()) {
            //分页
            Integer page = null;
            Integer size = null;
            if (null != userInfoParams.getPageAndSortParams().getPage()) {
                page = userInfoParams.getPageAndSortParams().getPage();
            }
            if (null != userInfoParams.getPageAndSortParams().getSize()) {
                size = userInfoParams.getPageAndSortParams().getSize();
            }
            //排序
            Sort sort = null;
            if (null != userInfoParams.getPageAndSortParams().getSort() && userInfoParams.getPageAndSortParams().getSort().size() > 0) {
                List<Sort.Order> orderList = new ArrayList<>();
                for (String key : userInfoParams.getPageAndSortParams().getSort().keySet()) {
                    if (ConstantEnum.SORT_ASC.getStringCode().equals(userInfoParams.getPageAndSortParams().getSort().get(key))) {
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
                return userInfoDao.findAll(specification, pageable).getContent();
            } else if (null != sort && (null == page || null == size)) {
                //无分页有排序
                return userInfoDao.findAll(specification, sort);
            } else if (null == sort && null != page && null != size) {
                //有分页无排序
                pageable = PageRequest.of(page, size);
                return userInfoDao.findAll(specification, pageable).getContent();
            } else {
                //无分页无排序
                return userInfoDao.findAll(specification);
            }
        } else {
            //无分页无排序
            return userInfoDao.findAll(specification);
        }
    }

    /**
     * 可选条件查询
     * (条件全空时不做查询)
     *
     * @param userInfoParams
     * @return
     */
    @Override
    public List<UserInfo> findByOptional(UserInfoParams userInfoParams) {
        //查询条件
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != userInfoParams.getUserPid()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.USER_PID), userInfoParams.getUserPid()));
            }
            if (null != userInfoParams.getStatus()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.STATUS), userInfoParams.getStatus()));
            }
            if (null != userInfoParams.getType()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.TYPE), userInfoParams.getType()));
            }
            if (null != userInfoParams.getUserAuth()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.USER_AUTH), userInfoParams.getUserAuth()));
            }
            if (null != userInfoParams.getRealName()) {
                predicateList.add(criteriaBuilder
                        .like(root.get(UserInfo_.REAL_NAME), "%" + userInfoParams.getRealName() + "%"));
            }
            if (StringUtils.isNotBlank(userInfoParams.getName())) {
                predicateList.add(criteriaBuilder
                        .like(root.get(UserInfo_.NAME), "%" + userInfoParams.getName() + "%"));
            }
            if (StringUtils.isNotBlank(userInfoParams.getPhone())) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.PHONE), userInfoParams.getPhone()));
            }
            if (StringUtils.isNotBlank(userInfoParams.getMail())) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.MAIL), userInfoParams.getMail()));
            }
            if (null != userInfoParams.getCreatedAt_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(UserInfo_.CREATED_AT), userInfoParams.getCreatedAt_start()));
            }
            if (null != userInfoParams.getCreatedAt_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(UserInfo_.CREATED_AT), userInfoParams.getCreatedAt_end()));
            }
            Predicate predicate = criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.where(predicate).distinct(true);
            return null;
        };
        //分页和排序
        Pageable pageable;
        if (null != userInfoParams.getPageAndSortParams()) {
            //分页
            Integer page = null;
            Integer size = null;
            if (null != userInfoParams.getPageAndSortParams().getPage()) {
                page = userInfoParams.getPageAndSortParams().getPage();
            }
            if (null != userInfoParams.getPageAndSortParams().getSize()) {
                size = userInfoParams.getPageAndSortParams().getSize();
            }
            //排序
            Sort sort = null;
            if (null != userInfoParams.getPageAndSortParams().getSort() && userInfoParams.getPageAndSortParams().getSort().size() > 0) {
                List<Sort.Order> orderList = new ArrayList<>();
                for (String key : userInfoParams.getPageAndSortParams().getSort().keySet()) {
                    if (ConstantEnum.SORT_ASC.getStringCode().equals(userInfoParams.getPageAndSortParams().getSort().get(key))) {
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
                return userInfoDao.findAll(specification, pageable).getContent();
            } else if (null != sort && (null == page || null == size)) {
                //无分页有排序
                return userInfoDao.findAll(specification, sort);
            } else if (null == sort && null != page && null != size) {
                //有分页无排序
                pageable = PageRequest.of(page, size);
                return userInfoDao.findAll(specification, pageable).getContent();
            } else {
                //无分页无排序
                return userInfoDao.findAll(specification);
            }
        } else {
            //无分页无排序
            return userInfoDao.findAll(specification);
        }
    }

    /**
     * 删除用户数据
     *
     * @param userId
     * @throws BaseException
     */
    @Override
    public void delete(Long userId,String ip) throws BaseException {
        //通过id查询是否存在此用户信息
        if (null == userId) {
            throw new BaseException(VALUE_NOT_COMPLETED);
        } else {
            Optional<UserInfo> searchUserInfo = userInfoDao.findById(userId);
            if (!searchUserInfo.isPresent()) {
                throw new BaseException(USER_NOT_EXISTED);
            } else {
                userInfoDao.deleteById(userId);
                //用户日志记录
                UserLog userLog = new UserLog();
                userLog.setUserId(userId);
                userLog.setMemo(LOG_MEMO_USER_DELETE.getStringCode());
                userLog.setIp(ip);
                userLog.setCreatedAt(new Date());//mysql升级至5.6.5+之后，该行代码可删除
                userLogDao.save(userLog);
            }
        }
    }

    /**
     * 统计用户量
     *
     * @return
     */
    @Override
    public Long count() {
        return userInfoDao.count();
    }

    /**
     * 根据条件统计用户量
     *
     * @param userInfoParams
     * @return
     */
    @Override
    public Long countByParams(UserInfoParams userInfoParams) {
        //查询条件
        Specification specification = specificationHandler(userInfoParams);
        return userInfoDao.count(specification);
    }

    /**
     * 用户注册
     *
     * @param userInfoParams
     */
    @Override
    public void register(UserInfoParams userInfoParams,String ip) throws BaseException {
        UserInfo userInfo = new UserInfo();
        //图形验证码校验(必填)
        picCaptchaHandler(userInfoParams);
        //邮箱校验(选填)
        if (StringUtils.isNotBlank(userInfoParams.getMail())) {
            if (!ValidatorUtils.isEmail(userInfoParams.getMail())) {
                throw new BaseException(MAIL_INCORRECT);
            } else {
                userInfo.setMail(userInfoParams.getMail());
            }
        }
        //用户名校验(必填)
        if (StringUtils.isBlank(userInfoParams.getName())) {
            throw new BaseException(USERNAME_IS_BLANK);
        } else {
            if (!ValidatorUtils.isUsername(userInfoParams.getName())) {
                throw new BaseException(USERNAME_INCORRECT);
            } else {
                userInfo.setName(userInfoParams.getName());
            }
        }
        //密码创建(必填)
        passwordHandler(userInfo, userInfoParams.getPassword());
        //手机号码校验(必填)
        if (StringUtils.isBlank(userInfoParams.getPhone()))
            throw new BaseException(PHONE_IS_BLANK);
        if (!ValidatorUtils.isMobile(userInfoParams.getPhone()))
            throw new BaseException(PHONE_INCORRECT);
        userInfo.setPhone(userInfoParams.getPhone());
        //手机验证码校验(必填)
        phoneCaptchaHandler(userInfoParams);
        //组装用户数据
        if (StringUtils.isNotBlank(userInfoParams.getUserGuid())) {
            userInfo.setUserGuid(userInfoParams.getUserGuid());
        } else {
            userInfo.setUserGuid(UUID.randomUUID().toString());
        }
        if (null != userInfoParams.getUserPid()) {
            userInfo.setUserPid(userInfoParams.getUserPid());
        }
        //设置默认用户状态
        userInfo.setStatus(USER_STATUS_NOT_ACTIVE.getIntCode());
        //设置默认用户权限
        userInfo.setUserAuth(USER_AUTH_NORMAL.getLongCode());
        if (null != userInfoParams.getType()) {
            userInfo.setType(userInfoParams.getType());
        }
        if (StringUtils.isNotBlank(userInfoParams.getRealName())) {
            userInfo.setRealName(userInfoParams.getRealName());
        }
        //查询该手机号/账号/邮箱是否注册过
        if (!isExistedUserInfo(userInfoParams.getName(), userInfoParams.getPhone(), userInfoParams.getMail())) {
            userInfo.setCreatedAt(new Date());//数据库版本提升5.6+后去掉，默认设置为timestamps
            UserInfo save = userInfoDao.save(userInfo);
            //用户日志记录
            UserLog userLog = new UserLog();
            userLog.setUserId(save.getUserId());
            userLog.setMemo(LOG_MEMO_USER_REGISTER.getStringCode());
            userLog.setIp(ip);
            userLog.setCreatedAt(new Date());//mysql升级至5.6.5+之后，该行代码可删除
            userLogDao.save(userLog);
        }
    }

    /**
     * 用户登录
     * 登录方式（1：用户名密码登录 2：短信验证码登录 ）
     * @param userInfoParams
     * @return
     * @throws BaseException
     */
    @Override
    public UserInfo login(UserInfoParams userInfoParams,String ip) throws BaseException {
        //图形验证码校验(必填)
        picCaptchaHandler(userInfoParams);
        //判断登录方式（1：用户名密码登录 2：短信验证码登录 ）
        UserInfoParams params = new UserInfoParams();
        if (null == userInfoParams.getLoginType())
            throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
        if (LOGIN_TYPE_USERNAME.getIntCode() == userInfoParams.getLoginType()){
            //登录方式1：用户名密码登录
            //用户名空值校验（必填）
            if (StringUtils.isBlank(userInfoParams.getName()))
                throw new BaseException(USERNAME_IS_BLANK);
            //密码空值校验（必填）
            if (StringUtils.isBlank(userInfoParams.getPassword()))
                throw new BaseException(PASSWORD_IS_BLANK);
            params.setName(userInfoParams.getName());
        }else if (LOGIN_TYPE_PHONE.getIntCode() == userInfoParams.getLoginType()){
            //登录方式2：短信验证码登录
            //手机号码格式校验(必填)
            if (StringUtils.isBlank(userInfoParams.getPhone()))
                throw new BaseException(PHONE_IS_BLANK);
            if (!ValidatorUtils.isMobile(userInfoParams.getPhone()))
                throw new BaseException(PHONE_INCORRECT);
            //短信验证码校验
            phoneCaptchaHandler(userInfoParams);
            params.setPhone(userInfoParams.getPhone());
        }else {
            //登录方式：暂不支持其他登录方式
            throw new BaseException(BaseExceptionEnum.LOGIN_TYPE_NOT_SUPPORT);
        }
        //根据条件查询用户
        List<UserInfo> userInfoList = findByOptional(params);
        if (null != userInfoList && userInfoList.size() == 1){
            UserInfo userInfo = userInfoList.get(0);
            //判断用户状态（仅已激活用户允许登录）
            if (USER_STATUS_ACTIVE.getIntCode() != userInfo.getStatus()){
                throw new BaseException(BaseExceptionEnum.USER_STATUS_NOT_ONLINE);
            }
            if (StringUtils.isNotBlank(userInfoParams.getPassword())){
                //密码校验
                String passwordOriginal = CryptionUtils.decryption(userInfo.getPassword(),userInfo.getSalt());
                if (!passwordOriginal.equals(userInfoParams.getPassword())) {
                    throw new BaseException(BaseExceptionEnum.PASSWORD_INCORRECT);
                }
            }
            //用户日志记录
            UserLog userLog = new UserLog();
            userLog.setUserId(userInfo.getUserId());
            userLog.setMemo(LOG_MEMO_USER_LOGIN.getStringCode());
            userLog.setIp(ip);
            userLog.setCreatedAt(new Date());//mysql升级至5.6.5+之后，该行代码可删除
            userLogDao.save(userLog);
            return userInfo;
        }else {
            throw new BaseException(BaseExceptionEnum.USER_NOT_EXISTED);
        }
    }

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     * @throws BaseException
     */
    @Override
    public UserInfo getUserInfo(Long userId) throws BaseException {
        if (null == userId) throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
        Optional<UserInfo> userInfoOptional = userInfoDao.findById(userId);
        if (!userInfoOptional.isPresent()) throw new BaseException(BaseExceptionEnum.USER_NOT_EXISTED);
        return userInfoOptional.get();
    }

    /**
     * 查询下一级子成员列表
     * @param userId
     * @return
     * @throws BaseException
     */
    @Override
    public List<UserInfo> getNextChildMembers(Long userId) throws BaseException {
        if (null == userId) throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
        UserInfoParams userInfoParams = new UserInfoParams();
        userInfoParams.setUserPid(userId);
        return findByParams(userInfoParams);
    }

    /**
     * 修改密码
     * @param passwordOld
     * @param passwordNew
     * @param userId
     * @throws BaseException
     */
    @Override
    public void resetPassword(String passwordOld, String passwordNew, Long userId) throws BaseException {
        if (null == userId || StringUtils.isBlank(passwordOld) || StringUtils.isBlank(passwordNew))
            throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
        //用户查询
        Optional<UserInfo> userInfoOptional = userInfoDao.findById(userId);
        if (!userInfoOptional.isPresent())
            throw new BaseException(BaseExceptionEnum.USER_NOT_EXISTED);
        //旧密码校验
        UserInfo userInfo = userInfoOptional.get();
        String passwordOriginal = CryptionUtils.decryption(userInfo.getPassword(), userInfo.getSalt());
        if (!passwordOriginal.equals(passwordOld)) {
            throw new BaseException(BaseExceptionEnum.PASSWORD_INCORRECT);
        }
        //新密码更新
        if (!ValidatorUtils.isPassword(passwordNew))
            throw new BaseException(PASSWORD_INCORRECT);
        //密码加密处理（生成私钥盐）
        PasswordKeys encryption = CryptionUtils.encryption(passwordNew);
        //更新密码
        userInfo.setPassword(encryption.getPasswordEncryption());
        userInfo.setSalt(encryption.getPrikey());
        userInfoDao.saveAndFlush(userInfo);
    }

    /**
     * 忘记密码（通过手机号码找回）
     * @param userInfoParams
     * @throws BaseException
     */
    @Override
    public void fogetPassword(UserInfoParams userInfoParams) throws BaseException {
        //手机号码校验(必填)
        if (StringUtils.isBlank(userInfoParams.getPhone()))
            throw new BaseException(PHONE_IS_BLANK);
        if (!ValidatorUtils.isMobile(userInfoParams.getPhone()))
            throw new BaseException(PHONE_INCORRECT);
        //查询用户
        UserInfo params = new UserInfo();
        params.setPhone(userInfoParams.getPhone());
        Example<UserInfo> example = Example.of(params);
        Optional<UserInfo> userInfoOptional = userInfoDao.findOne(example);
        if (!userInfoOptional.isPresent())
            throw new BaseException(BaseExceptionEnum.USER_NOT_EXISTED);
        UserInfo userInfo = userInfoOptional.get();
        //手机验证码校验(必填)
        phoneCaptchaHandler(userInfoParams);
        //密码创建(必填)
        passwordHandler(userInfo, userInfoParams.getPassword());
        userInfoDao.saveAndFlush(userInfo);
    }

    /**
     * 更改下一级子用户状态
     * @param userInfoParams
     * @throws BaseException
     */
    @Override
    public void updateStatus(UserInfoParams userInfoParams) throws BaseException {
        //判断是否上下级
        Optional<UserInfo> userInfoOptional = userInfoDao.findById(userInfoParams.getUserId());
        UserInfo userInfo = userInfoOptional.get();
        if (!userInfoOptional.isPresent() || userInfoParams.getUserPid() != userInfo.getUserPid())
            throw new BaseException(BaseExceptionEnum.USER_NOT_EXISTED);
        //更改用户状态
        userInfo.setStatus(userInfoParams.getStatus());
        userInfoDao.saveAndFlush(userInfo);
    }

    /**
     * 更改手机号码
     * @param userInfoParams
     * @throws BaseException
     */
    @Override

    public void resetPhone(UserInfoParams userInfoParams) throws BaseException {
        //手机验证码校验(必填)
        phoneCaptchaHandler(userInfoParams);
        //查询用户信息
        if (null == userInfoParams.getUserId())
            throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
        Optional<UserInfo> userInfoOptional = userInfoDao.findById(userInfoParams.getUserId());
        if (!userInfoOptional.isPresent())
            throw new BaseException(BaseExceptionEnum.USER_NOT_EXISTED);
        UserInfo userInfo = userInfoOptional.get();
        //手机号码校验(必填)
        if (StringUtils.isBlank(userInfoParams.getPhone())) {
            throw new BaseException(PHONE_IS_BLANK);
        } else {
            if (!ValidatorUtils.isMobile(userInfoParams.getPhone())) {
                throw new BaseException(PHONE_INCORRECT);
            } else {
                userInfo.setPhone(userInfoParams.getPhone());
            }
        }
        userInfoDao.saveAndFlush(userInfo);
    }

    /**
     * 获取推广二维码
     * @param taskInfoParams
     * @return
     * @throws Exception
     */
    @Override
    public String getQRCode(TaskInfoParams taskInfoParams) throws Exception {
        //查询项目
        Optional<ProjectInfo> projectInfoOptional = projectInfoDao.findById(taskInfoParams.getProjectId());
        if (!projectInfoOptional.isPresent() || null == projectInfoOptional.get().getUrlType() || StringUtils.isBlank(projectInfoOptional.get().getUrlGen()))
            throw new BaseException(BaseExceptionEnum.PROJECT_NOT_EXISTED);
        ProjectInfo projectInfo = projectInfoOptional.get();
        //构建二维码生成模型
        QRCodeParams qrCodeParams = new QRCodeParams();
        //判断url类型(URL类型：1公共URL 2私有URL)
        if (URL_TYPE_PUBLIC.getIntCode() == projectInfo.getUrlType()){
            qrCodeParams.setContent(projectInfo.getUrlGen());
            logger.info("公共URL为：" + projectInfo.getUrlGen());
        }else if (URL_TYPE_SELF.getIntCode() == projectInfo.getUrlType()){
            //查询用户信息
            Optional<UserInfo> userInfoOptional = userInfoDao.findById(taskInfoParams.getUserId());
            if (!userInfoOptional.isPresent() || StringUtils.isBlank(userInfoOptional.get().getUserGuid()))
                throw new BaseException(BaseExceptionEnum.USER_NOT_EXISTED);
            logger.info("私有URL为：" + projectInfo.getUrlGen() + userInfoOptional.get().getUserGuid());
            qrCodeParams.setContent(projectInfo.getUrlGen() + userInfoOptional.get().getUserGuid());
        }else {
            throw new BaseException(BaseExceptionEnum.URL_TYPE_NOT_SUPPORT);
        }
        return qrCodeService.drawQRCode(qrCodeParams);
    }

    /**
     * 图形验证码处理器（校验）
     * @param userInfoParams
     * @throws BaseException
     */
    private void picCaptchaHandler(UserInfoParams userInfoParams) throws BaseException {
        if (StringUtils.isBlank(userInfoParams.getPicCaptcha()) || StringUtils.isBlank(userInfoParams.getPicCaptchaId())) {
            throw new BaseException(CAPTCHA_INCORRECT);
        } else {
            //校验
            String cacheCaptcha = (String) redisTemplate.opsForValue().get(userInfoParams.getPicCaptchaId());
            if (StringUtils.isBlank(cacheCaptcha)) {
                throw new BaseException(CAPTCHA_EXPIRED);
            } else {
                if (!StringUtils.equals(cacheCaptcha, userInfoParams.getPicCaptcha())) {
                    throw new BaseException(CAPTCHA_INCORRECT);
                }
            }
        }
    }

    /**
     * 手机验证码处理器（校验）
     * @param userInfoParams
     * @throws BaseException
     */
    private void phoneCaptchaHandler(UserInfoParams userInfoParams) throws BaseException {
        if (StringUtils.isBlank(userInfoParams.getSmsCaptcha()) || StringUtils.isBlank(userInfoParams.getSmsCaptchaId())) {
            throw new BaseException(SMS_CAPTCHA_INCORRECT);
        } else {
            //校验
            String cacheSMSCaptcha = (String) redisTemplate.opsForValue().get(userInfoParams.getSmsCaptchaId());
            if (StringUtils.isBlank(cacheSMSCaptcha)) {
                throw new BaseException(SMS_CAPTCHA_EXPIRED);
            } else {
                if (!StringUtils.equals(cacheSMSCaptcha, userInfoParams.getSmsCaptcha())) {
                    throw new BaseException(SMS_CAPTCHA_INCORRECT);
                }
            }
        }
    }

    /**
     * 密码处理器（创建）
     * @param userInfo
     * @param password
     * @throws BaseException
     */
    private void passwordHandler(UserInfo userInfo, String password) throws BaseException {
        if (StringUtils.isBlank(password)) {
            throw new BaseException(PASSWORD_IS_BLANK);
        } else {
            if (!ValidatorUtils.isPassword(password)) {
                throw new BaseException(PASSWORD_INCORRECT);
            } else {
                //密码加密处理（生成私钥盐）
                PasswordKeys encryption = CryptionUtils.encryption(password);
                userInfo.setPassword(encryption.getPasswordEncryption());
                userInfo.setSalt(encryption.getPrikey());
            }
        }
    }

    /**
     * 通用条件构造处理器（创建）
     * @param userInfoParams
     * @return
     */
    private Specification specificationHandler(UserInfoParams userInfoParams) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != userInfoParams.getUserId()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.USER_ID), userInfoParams.getUserId()));
            }
            if (null != userInfoParams.getUserGuid()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.USER_GUID), userInfoParams.getUserGuid()));
            }
            if (null != userInfoParams.getUserPid()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.USER_PID), userInfoParams.getUserPid()));
            }
            if (null != userInfoParams.getStatus()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.STATUS), userInfoParams.getStatus()));
            }
            if (null != userInfoParams.getType()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.TYPE), userInfoParams.getType()));
            }
            if (null != userInfoParams.getUserAuth()) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.USER_AUTH), userInfoParams.getUserAuth()));
            }
            if (null != userInfoParams.getRealName()) {
                predicateList.add(criteriaBuilder
                        .like(root.get(UserInfo_.REAL_NAME), "%" + userInfoParams.getRealName() + "%"));
            }
            if (StringUtils.isNotBlank(userInfoParams.getName())) {
                predicateList.add(criteriaBuilder
                        .like(root.get(UserInfo_.NAME), "%" + userInfoParams.getName() + "%"));
            }
            if (StringUtils.isNotBlank(userInfoParams.getPhone())) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.PHONE), userInfoParams.getPhone()));
            }
            if (StringUtils.isNotBlank(userInfoParams.getMail())) {
                predicateList.add(criteriaBuilder
                        .equal(root.get(UserInfo_.MAIL), userInfoParams.getMail()));
            }
            if (null != userInfoParams.getCreatedAt_start()) {
                predicateList.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(UserInfo_.CREATED_AT), userInfoParams.getCreatedAt_start()));
            }
            if (null != userInfoParams.getCreatedAt_end()) {
                predicateList.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(UserInfo_.CREATED_AT), userInfoParams.getCreatedAt_end()));
            }
            Predicate predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.where(predicate).distinct(true);
            return null;
        };
        return specification;
    }
}
