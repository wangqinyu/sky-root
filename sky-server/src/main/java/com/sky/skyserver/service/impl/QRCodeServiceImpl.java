package com.sky.skyserver.service.impl;

import com.google.zxing.WriterException;
import com.sky.skyentity.bean.QRCodeParams;
import com.sky.skyserver.service.inter.QRCodeService;
import com.sky.skyserver.util.qrcode.util.NumUtil;
import com.sky.skyserver.util.qrcode.wrapper.QrCodeGenWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.sky.skyserver.common.ConstantEnum.*;

@Service
@Transactional
public class QRCodeServiceImpl implements QRCodeService {
    private Logger logger = LoggerFactory.getLogger(QRCodeServiceImpl.class);

    /**
     * 生成二维码
     * @param qrCodeParams
     * @return
     * @throws IOException
     * @throws WriterException
     */
    @Override
    public String drawQRCode(QRCodeParams qrCodeParams) throws IOException, WriterException {
        if (null == qrCodeParams.getSize())
            qrCodeParams.setSize(QRCODE_SIZE_200.getIntCode());
        if (null == qrCodeParams.getPreColor())
            qrCodeParams.setPreColor(COLOR_BLACK.getStringCode());
        if (null == qrCodeParams.getBgColor())
            qrCodeParams.setBgColor(COLOR_WHITE.getStringCode());
        if (null == qrCodeParams.getStyle())
            qrCodeParams.setStyle(QRCODE_STYLE_RECT.getStringCode());
        String bufferedImage = QrCodeGenWrapper.of(qrCodeParams.getContent())
                .setW(qrCodeParams.getSize())
                .setH(qrCodeParams.getSize())
                .setDrawPreColor(NumUtil.decode2int(qrCodeParams.getPreColor(),16))
                .setDrawBgColor(NumUtil.decode2int(qrCodeParams.getBgColor(),16))
                .setDrawStyle(qrCodeParams.getStyle())
                .asString();
        logger.info("qrcode的base64编码为："+bufferedImage);
        return bufferedImage;
    }
}
