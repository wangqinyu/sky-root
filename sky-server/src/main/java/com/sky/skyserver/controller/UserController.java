package com.sky.skyserver.controller;

import com.sky.skyentity.bean.TaskInfoParams;
import com.sky.skyentity.bean.UserInfoParams;
import com.sky.skyentity.entity.UserLog;
import com.sky.skyserver.common.BaseDataResp;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyserver.service.inter.UserInfoService;
import com.sky.skyentity.entity.UserInfo;
import com.sky.skyserver.service.inter.UserLogService;
import com.sky.skyserver.util.InternetUtils;
import com.sky.skyserver.util.security.PrivacyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

import static com.sky.skyserver.common.BaseExceptionEnum.SESSION_LOGIN_EXPIRED;
import static com.sky.skyserver.common.ConstantEnum.LOG_MEMO_USER_LOGOUT;
import static org.apache.commons.configuration.FileOptionsProvider.CURRENT_USER;

@RestController
@RequestMapping("/user")
@Api(value = "用户管理", description = "用户管理API")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserLogService userLogService;
    //==============1、平台基础操作接口(不对普通用户开放)==============

    @ApiOperation(value = "保存(admin)", notes = "保存用户数据")
    @RequestMapping(value = "/admin/save", method = {RequestMethod.POST})
    public BaseDataResp save(UserInfo userInfo,HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            if (null == userInfo)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            String ip = InternetUtils.getIpAddress(request);
            userInfoService.save(userInfo,ip);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "更新(admin)", notes = "更新用户数据")
    @RequestMapping(value = "/admin/update", method = {RequestMethod.POST})
    public BaseDataResp update(UserInfo userInfo, HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            if (null == userInfo)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            String ip = InternetUtils.getIpAddress(request);
            userInfoService.update(userInfo,ip);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "查询全部(admin)", notes = "查询全部用户数据")
    @RequestMapping(value = "/admin/findAll", method = {RequestMethod.POST})
    public BaseDataResp<List<UserInfo>> findAll() {
        BaseDataResp baseDataResp;
        try {
            List<UserInfo> userInfoList = userInfoService.findAll();
            baseDataResp = new BaseDataResp("查询成功",PrivacyUtils.privacyAll(userInfoList));
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "条件查询(admin)", notes = "按条件AND查询用户数据")
    @RequestMapping(value = "/admin/findByParams", method = {RequestMethod.POST})
    public BaseDataResp<List<UserInfo>> findByParams(UserInfoParams userInfoParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == userInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<UserInfo> userInfoList = userInfoService.findByParams(userInfoParams);
            baseDataResp = new BaseDataResp("查询成功",PrivacyUtils.privacyAll(userInfoList));
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "可选条件查询(admin)", notes = "通过多个可选条件OR查询用户数据")
    @RequestMapping(value = "/admin/findByOptional", method = {RequestMethod.POST})
    public BaseDataResp<List<UserInfo>> findByOptional(UserInfoParams userInfoParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == userInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<UserInfo> userInfoList = userInfoService.findByOptional(userInfoParams);
            baseDataResp = new BaseDataResp("查询成功",PrivacyUtils.privacyAll(userInfoList));
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "删除(admin)", notes = "删除用户数据")
    @RequestMapping(value = "/admin/delete", method = {RequestMethod.POST})
    public BaseDataResp delete(Long userId, HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            String ip = InternetUtils.getIpAddress(request);
            userInfoService.delete(userId,ip);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "统计(admin)", notes = "统计用户量")
    @RequestMapping(value = "/admin/count", method = {RequestMethod.POST})
    public BaseDataResp count() {
        BaseDataResp baseDataResp;
        try {
            Long userAmount = userInfoService.count();
            baseDataResp = new BaseDataResp("操作成功",userAmount);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "条件统计(admin)", notes = "根据条件统计用户量")
    @RequestMapping(value = "/admin/countByParams", method = {RequestMethod.POST})
    public BaseDataResp countByParams(UserInfoParams userInfoParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == userInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            Long userAmount = userInfoService.countByParams(userInfoParams);
            baseDataResp = new BaseDataResp("操作成功",userAmount);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    //==================2、功能操作接口(对外开放)====================

    @ApiOperation(value = "注册", notes = "用户注册")
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public BaseDataResp register(UserInfoParams userInfoParams, HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            if (null == userInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            String ip = InternetUtils.getIpAddress(request);
            userInfoService.register(userInfoParams,ip);
            baseDataResp = new BaseDataResp("注册成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "登录", notes = "用户登录（登录类型 1：用户名密码 2：短信验证码 ）")
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public BaseDataResp login(UserInfoParams userInfoParams, HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            if (null == userInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            String ip = InternetUtils.getIpAddress(request);
            UserInfo userInfo = userInfoService.login(userInfoParams,ip);
            HttpSession session = request.getSession();
            session.setAttribute(CURRENT_USER, userInfo.getUserId());
            logger.info("【登陆成功】用户Id：" + userInfo.getUserId());
            baseDataResp = new BaseDataResp("登录成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "登出", notes = "用户登出")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public BaseDataResp logout(HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (null == session)
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            long userId = (long) session.getAttribute(CURRENT_USER);
            String ip = InternetUtils.getIpAddress(request);
            session.invalidate();
            //用户日志记录
            UserLog userLog = new UserLog();
            userLog.setUserId(userId);
            userLog.setMemo(LOG_MEMO_USER_LOGOUT.getStringCode());
            userLog.setIp(ip);
            userLogService.save(userLog);
            baseDataResp = new BaseDataResp("登出成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "获取用户信息", notes = "获取登录用户信息")
    @RequestMapping(value = "/getUserInfo", method = {RequestMethod.POST})
    public BaseDataResp getUserInfo(HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            long userId = (long) session.getAttribute(CURRENT_USER);
            UserInfo userInfo = userInfoService.getUserInfo(userId);
            int maxInactiveInterval = session.getMaxInactiveInterval();
            logger.info("Session超时时间(秒)："+String.valueOf(maxInactiveInterval));
            baseDataResp = new BaseDataResp("操作成功",PrivacyUtils.privacy(userInfo));
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "查询下一级子成员列表", notes = "查询下一级子成员列表")
    @RequestMapping(value = "/getNextChildMembers", method = {RequestMethod.POST})
    public BaseDataResp getNextChildMembers(HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            long userId = (long) session.getAttribute(CURRENT_USER);
            List<UserInfo> userInfoList = userInfoService.getNextChildMembers(userId);
            baseDataResp = new BaseDataResp("查询成功",PrivacyUtils.privacyAll(userInfoList));
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "更新", notes = "更新用户数据")
    @RequestMapping(value = "/updateUserInfo", method = {RequestMethod.POST})
    public BaseDataResp updateUserInfo(UserInfo userInfo,HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            long userId = (long) session.getAttribute(CURRENT_USER);
            String ip = InternetUtils.getIpAddress(request);
            if (null == userInfo)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            userInfo.setUserId(userId);
            userInfoService.update(userInfo,ip);
            baseDataResp = new BaseDataResp("更新成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "修改密码", notes = "修改登录密码")
    @RequestMapping(value = "/resetPassword", method = {RequestMethod.POST})
    public BaseDataResp resetPassword(String passwordOld,String passwordNew,HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            long userId = (long) session.getAttribute(CURRENT_USER);
            userInfoService.resetPassword(passwordOld,passwordNew,userId);
            baseDataResp = new BaseDataResp("更新成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "忘记密码", notes = "忘记登录密码（通过手机号码找回）")
    @RequestMapping(value = "/fogetPassword", method = {RequestMethod.POST})
    public BaseDataResp fogetPassword(UserInfoParams userInfoParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == userInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            userInfoService.fogetPassword(userInfoParams);
            baseDataResp = new BaseDataResp("找回成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "更改下一级子用户状态", notes = "更改下一级子用户状态")
    @RequestMapping(value = "/resetNextChildMemberStatus", method = {RequestMethod.POST})
    public BaseDataResp resetNextChildMemberStatus(UserInfoParams userInfoParams,HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            long userId = (long) session.getAttribute(CURRENT_USER);
            if (null == userInfoParams || null == userInfoParams.getUserPid() || null == userInfoParams.getUserId() || null == userInfoParams.getStatus())
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            if (userId != userInfoParams.getUserPid().longValue())
                throw new BaseException(BaseExceptionEnum.USER_NOT_EXISTED);
            userInfoService.updateStatus(userInfoParams);
            baseDataResp = new BaseDataResp("状态更新成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "更改手机号码", notes = "更改手机号码")
    @RequestMapping(value = "/resetPhone", method = {RequestMethod.POST})
    public BaseDataResp resetPhone(UserInfoParams userInfoParams,HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            long userId = (long) session.getAttribute(CURRENT_USER);
            if (null == userInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            userInfoParams.setUserId(userId);
            userInfoService.resetPhone(userInfoParams);
            baseDataResp = new BaseDataResp("更新成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "获取推广二维码", notes = "获取推广二维码")
    @RequestMapping(value = "/getQRCode", method = {RequestMethod.POST})
    public BaseDataResp getQRCode(TaskInfoParams taskInfoParams,HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            if (null == taskInfoParams || null == taskInfoParams.getUserId() || null == taskInfoParams.getProjectId())
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            String qrCodeImag = userInfoService.getQRCode(taskInfoParams);
            baseDataResp = new BaseDataResp("操作成功",qrCodeImag);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }
}
