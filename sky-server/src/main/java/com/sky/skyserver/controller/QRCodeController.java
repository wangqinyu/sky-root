package com.sky.skyserver.controller;

import com.sky.skyentity.bean.QRCodeParams;
import com.sky.skyserver.common.BaseDataResp;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyserver.service.inter.QRCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
@Api(value = "二维码", description = "二维码API")
public class QRCodeController {
    private Logger logger = LoggerFactory.getLogger(QRCodeController.class);
    @Autowired
    QRCodeService qrCodeService;

    @ApiOperation(value = "生成二维码(BASE64)", notes = "生成二维码")
    @RequestMapping(value = "/drawQRCode", method = {RequestMethod.POST})
    public BaseDataResp drawQRCode(QRCodeParams qrCodeParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == qrCodeParams || StringUtils.isBlank(qrCodeParams.getContent()))
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            logger.info("二维码内容为：" + qrCodeParams.getContent());
            String qcodeImag = qrCodeService.drawQRCode(qrCodeParams);
            baseDataResp = new BaseDataResp("操作成功",qcodeImag);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }
}
