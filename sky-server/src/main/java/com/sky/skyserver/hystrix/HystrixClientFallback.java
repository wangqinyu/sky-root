/*
package com.sky.skyserver.hystrix;

import com.sky.skyentity.bean.QuerySMSSendResp;
import com.sky.skyentity.bean.SendSMSResp;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyserver.feign.SMSFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class HystrixClientFallback implements SMSFeign {
    Logger logger = LoggerFactory.getLogger(HystrixClientFallback.class);
    //加Hystrix熔断器  参考https://www.jianshu.com/p/6ca2b31798cf
    @Override
    public SendSMSResp sendSMS(QuerySMSSendResp sendSMSReq) throws BaseException{
        logger.error("Hystrix fallback ...");
        throw new BaseException(BaseExceptionEnum.INTERNET_ERROR);
    }
}
*/
