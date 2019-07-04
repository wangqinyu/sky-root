package com.sky.skyserver.controller;

import com.sky.skyentity.bean.PicCaptchaResp;
import com.sky.skyentity.bean.QuerySMSSendResp;
import com.sky.skyentity.bean.SendSMSResp;
import com.sky.skyserver.common.BaseDataResp;
import com.sky.skyserver.service.inter.CheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
@Api(value = "校验", description = "校验API")
public class CheckController {
    private Logger logger = LoggerFactory.getLogger(CheckController.class);
    @Autowired
    CheckService checkService;

    @ApiOperation(value = "发送短信验证码", notes = "给用户手机发送短信验证码")
    @RequestMapping(value = "/sendSMS", method = {RequestMethod.POST})
    public BaseDataResp sendSMS(String phone) {
        BaseDataResp baseDataResp;
        try {
            SendSMSResp sendSMSResp = checkService.sendSMS(phone);
            baseDataResp = new BaseDataResp("操作成功",sendSMSResp);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "查询短信验证码", notes = "给用户手机发送短信验证码")
    @RequestMapping(value = "/querySMS", method = {RequestMethod.POST})
    public BaseDataResp querySMS(String phone,String BizId) {
        BaseDataResp baseDataResp;
        try {
            QuerySMSSendResp querySMSSendResp = checkService.querySMS(phone, BizId);
            baseDataResp = new BaseDataResp("操作成功",querySMSSendResp);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "获取图形验证码", notes = "获取图形验证码")
    @RequestMapping(value = "/getCaptcha", method = {RequestMethod.GET})
    public BaseDataResp getCaptcha(){
        BaseDataResp baseDataResp;
        try {
            PicCaptchaResp captcha = checkService.getCaptcha();
            baseDataResp = new BaseDataResp("获取成功",captcha);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }
}
