package com.sky.skyadapterkidcrm.hystrix;/*

import com.sky.skyadapterkidcrm.common.BaseExceptionEnum;
import com.sky.skyadapterkidcrm.exception.BaseException;
import com.sky.skyadapterkidcrm.feign.AdapterFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class HystrixClientFallback implements AdapterFeign {
    Logger logger = LoggerFactory.getLogger(HystrixClientFallback.class);
    //加Hystrix熔断器  参考https://www.jianshu.com/p/6ca2b31798cf
    @Override
    public String sendSMS(QuerySMSSendResp sendSMSReq) throws BaseException {
        logger.error("Hystrix fallback ...");
        throw new BaseException(BaseExceptionEnum.INTERNET_ERROR);
    }
}
*/