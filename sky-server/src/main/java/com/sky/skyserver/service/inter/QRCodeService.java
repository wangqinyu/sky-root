package com.sky.skyserver.service.inter;

import com.google.zxing.WriterException;
import com.sky.skyentity.bean.QRCodeParams;

import java.io.IOException;

public interface QRCodeService {
    //生成二维码
    String drawQRCode(QRCodeParams qrCodeParams) throws IOException, WriterException;
}
