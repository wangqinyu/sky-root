/*
package com.sky.skyserver.feign;

import com.alibaba.fastjson.JSONObject;
import com.sky.skyserver.config.FeignConfig;
import com.sky.skyserver.exception.BaseException;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${common.postsms.name}",value = "${common.postsms.name}",url = "${common.postsms.url}", configuration = FeignConfig.class)
public interface SMSFeign {
    @RequestMapping(method = RequestMethod.POST)
    JSONObject sendSMS(@RequestParam(name = "PhoneNumbers") String PhoneNumbers,
                       @RequestParam(name = "SignName") String SignName,
                       @RequestParam(name = "TemplateCode") String TemplateCode,
                       @RequestParam(name = "TemplateParam") String TemplateParam,
                       @RequestParam(name = "Version") String Version,
                       @RequestParam(name = "Action") String Action) throws BaseException;
}
*/
