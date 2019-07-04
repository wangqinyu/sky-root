package com.sky.skyserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.sky.skyentity.bean.*;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyserver.service.inter.CheckService;
import com.sky.skyserver.util.DateUtils;
import com.sky.skyserver.util.ValidatorUtils;
import com.sky.skyserver.util.captcha.Captcha;
import com.sky.skyserver.util.captcha.GifCaptcha;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.sky.skyserver.common.BaseExceptionEnum.*;
import static com.sky.skyserver.common.ConstantEnum.*;

@Service
@Transactional
public class CheckServiceImpl implements CheckService {
    private Logger logger = LoggerFactory.getLogger(CheckServiceImpl.class);
    @Autowired
    RedisTemplate redisTemplate;
    //请求地址Url
    @Value(value = "${common.sms.url}")
    private String Url;
    //用户AccessKeyId
    @Value(value = "${common.sms.accessKeyId}")
    private String AccessKeyId;
    //用户SecretAccessKey
    @Value(value = "${common.sms.secretAccessKey}")
    private String SecretAccessKey;
    //短信签名名称
    @Value(value = "${common.sms.signName}")
    private String SignName;
    //短信模板ID
    @Value(value = "${common.sms.templateCode}")
    private String TemplateCode;
    //API 的版本号，格式为 YYYY-MM-DD
    @Value(value = "${common.sms.version}")
    private String Version;
    //发送短信 API 的方法名称
    @Value(value = "${common.sms.postAction}")
    private String PostAction;
    //查询短信 API 的方法名称
    @Value(value = "${common.sms.queryAction}")
    private String QueryAction;
/*    @Autowired
    SMSFeign smsFeign;*/

    /**
     * 发送短信验证码
     *
     * @param phone
     * @throws BaseException
     */
    @Override
    public SendSMSResp sendSMS(String phone) throws Exception {
        if (StringUtils.isNotBlank(phone) && ValidatorUtils.isMobile(phone)) {
            //生成短信验证码
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < 6; i++) {
                stringBuffer.append(String.valueOf((int) Math.floor(Math.random() * 10)));
            }
            logger.info("验证码：" + stringBuffer.toString());
            //调用阿里云OpenAPI发送短信验证码
            DefaultProfile profile = DefaultProfile.getProfile("default", AccessKeyId, SecretAccessKey);
            IAcsClient client = new DefaultAcsClient(profile);

            CommonRequest request = new CommonRequest();
            //request.setProtocol(ProtocolType.HTTPS);
            request.setMethod(MethodType.POST);
            request.setDomain(Url);
            request.setVersion(Version);
            request.setAction(PostAction);
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", SignName);
            request.putQueryParameter("TemplateCode", TemplateCode);
            request.putQueryParameter("TemplateParam", "{\"code\":\"" + stringBuffer.toString() + "\"}");
            CommonResponse response = client.getCommonResponse(request);
            SendSMSResp sendSMSResp = JSONObject.toJavaObject(JSON.parseObject(response.getData()), SendSMSResp.class);
            if (null != sendSMSResp && StringUtils.isNotBlank(sendSMSResp.getCode()) && StringUtils.equals(sendSMSResp.getCode(),SMS_SUCCESS.getStringCode())) {
                //存储Redis缓存
                redisTemplate.opsForValue().set(sendSMSResp.getRequestId(), stringBuffer.toString(), REDIS_VALIDTIME_FIVE_MINUTE.getLongCode(), TimeUnit.MINUTES);
                return sendSMSResp;
            } else {
                throw new BaseException(SMS_SEND_FAILED);
            }
        } else {
            throw new BaseException(PHONE_INCORRECT);
        }
    }

    /**
     * 查询短信验证码发送详情
     * @param phone
     * @param BizId
     * @return
     * @throws Exception
     */
    @Override
    public QuerySMSSendResp querySMS(String phone, String BizId) throws Exception {
        if (StringUtils.isNotBlank(phone) && ValidatorUtils.isMobile(phone) && StringUtils.isNotBlank(BizId)) {
            //调用阿里云OpenAPI查询短信验证码
            DefaultProfile profile = DefaultProfile.getProfile("default", AccessKeyId, SecretAccessKey);
            IAcsClient client = new DefaultAcsClient(profile);

            CommonRequest request = new CommonRequest();
            //request.setProtocol(ProtocolType.HTTPS);
            request.setMethod(MethodType.POST);
            request.setDomain(Url);
            request.setVersion(Version);
            request.setAction(QueryAction);
            request.putQueryParameter("PhoneNumber", phone);
            request.putQueryParameter("SendDate", DateUtils.currentDayWithOutLine());
            request.putQueryParameter("PageSize",PAGE_SIZE_TWENTY.getStringCode());
            request.putQueryParameter("CurrentPage",PAGE_NUM_FIRST.getStringCode());
            request.putQueryParameter("BizId", BizId);
            CommonResponse response = client.getCommonResponse(request);
            //取出数据
            JSONObject result = JSON.parseObject(response.getData());
            QuerySMSSendResp querySMSSendResp = JSONObject.toJavaObject(result, QuerySMSSendResp.class);
            //取出smsSendDetailDTOs对象
            JSONObject smsSendDetailDTOsJsonObject = JSON.parseObject(result.get("SmsSendDetailDTOs").toString());
            //取出SmsSendDetailDTO列表
            JSONArray smsSendDetailDTOList = JSON.parseArray(smsSendDetailDTOsJsonObject.get("SmsSendDetailDTO").toString());
            //组装QuerySMSSendDetailResp列表
            List<QuerySMSSendDetailResp> querySMSSendDetailRespList = new ArrayList<>();
            for (Object smsSendDetailDTO : smsSendDetailDTOList) {
                querySMSSendDetailRespList.add(JSON.toJavaObject((JSON) smsSendDetailDTO, QuerySMSSendDetailResp.class));
            }
            //组装QuerySMSSendDetailListResp对象
            QuerySMSSendDetailListResp querySMSSendDetailListResp = new QuerySMSSendDetailListResp();
            querySMSSendDetailListResp.setQuerySMSSendDetailRespList(querySMSSendDetailRespList);
            //设置QuerySMSSendResp中属性的值
            querySMSSendResp.setQuerySMSSendDetailListResp(querySMSSendDetailListResp);
            return querySMSSendResp;
        } else {
            throw new BaseException(VALUE_ERROR);
        }
    }

    /**
     * 获取图形验证码
     *
     * @return
     * @throws Exception
     */
    @Override
    public PicCaptchaResp getCaptcha() throws Exception {
        PicCaptchaResp picCaptchaResp = new PicCaptchaResp();
        //设置返回数据
        Captcha captcha = new GifCaptcha();
        String gifByBase64 = captcha.getPicByBase64();
        logger.info("验证码为：" + captcha.text());
        logger.info("Base64编码为：\n" + gifByBase64);
        //存储Redis缓存
        String uuid = UUID.randomUUID().toString();
        logger.info("验证码ID为：" + uuid);
        redisTemplate.opsForValue().set(uuid, captcha.text(),REDIS_VALIDTIME_FIVE_MINUTE.getLongCode(), TimeUnit.MINUTES);
        picCaptchaResp.setCaptchaId(uuid);
        picCaptchaResp.setCaptchaBase64(gifByBase64);
        return picCaptchaResp;
    }
}
