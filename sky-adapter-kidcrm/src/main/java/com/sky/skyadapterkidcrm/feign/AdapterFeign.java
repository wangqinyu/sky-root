package com.sky.skyadapterkidcrm.feign;

import com.sky.skyadapterkidcrm.bean.KidcrmReq;
import com.sky.skyadapterkidcrm.bean.KidcrmResp;
import com.sky.skyadapterkidcrm.config.FeignConfig;
import com.sky.skyadapterkidcrm.exception.BaseException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${common.kidcrm.name}",value = "${common.kidcrm.name}",url = "${common.kidcrm.url}", configuration = FeignConfig.class)
public interface AdapterFeign {
    @RequestMapping(method = RequestMethod.POST)
    KidcrmResp searchOne(@RequestBody KidcrmReq kidcrmReq) throws BaseException;
}
