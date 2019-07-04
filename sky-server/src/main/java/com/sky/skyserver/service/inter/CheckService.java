package com.sky.skyserver.service.inter;

import com.sky.skyentity.bean.PicCaptchaResp;
import com.sky.skyentity.bean.QuerySMSSendResp;
import com.sky.skyentity.bean.SendSMSResp;

public interface CheckService {
    //发送短信验证码
    SendSMSResp sendSMS(String phone) throws Exception;

    //查询短信验证码
    QuerySMSSendResp querySMS(String phone, String BizId) throws Exception;

    //获取图形验证码
    PicCaptchaResp getCaptcha() throws Exception;


}
