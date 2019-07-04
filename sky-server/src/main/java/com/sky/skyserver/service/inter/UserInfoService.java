package com.sky.skyserver.service.inter;

import com.sky.skyentity.bean.TaskInfoParams;
import com.sky.skyentity.bean.UserInfoParams;
import com.sky.skyentity.entity.UserInfo;
import com.sky.skyserver.exception.BaseException;

import java.util.List;

public interface UserInfoService {
    //保存用户数据
    void save(UserInfo userInfo,String ip) throws BaseException;
    //更新用户数据
    void update(UserInfo userInfo,String ip) throws BaseException;
    //查询全部用户数据
    List<UserInfo> findAll();
    //按条件查询用户数据
    List<UserInfo> findByParams(UserInfoParams userInfoParams);
    //可选条件查询
    List<UserInfo> findByOptional(UserInfoParams userInfoParams);
    //删除用户数据
    void delete(Long userId,String ip) throws BaseException;
    //统计用户量
    Long count();
    //根据条件统计用户量
    Long countByParams(UserInfoParams userInfoParams);
    //用户注册
    void register(UserInfoParams userInfoParams,String ip) throws BaseException;
    //用户登录
    UserInfo login(UserInfoParams userInfoParams,String ip) throws BaseException;
    //获取用户信息
    UserInfo getUserInfo(Long userId) throws BaseException;
    //查询下一级子成员列表
    List<UserInfo> getNextChildMembers(Long userId) throws BaseException;
    //修改密码
    void resetPassword(String passwordOld, String passwordNew, Long userId) throws BaseException;
    //忘记密码（通过手机号码找回）
    void fogetPassword(UserInfoParams userInfoParams) throws BaseException;
    //更改下一级子用户状态
    void updateStatus(UserInfoParams userInfoParams) throws BaseException;
    //更改手机号码
    void resetPhone(UserInfoParams userInfoParams) throws BaseException;
    //获取推广二维码
    String getQRCode(TaskInfoParams taskInfoParams) throws Exception;
}
