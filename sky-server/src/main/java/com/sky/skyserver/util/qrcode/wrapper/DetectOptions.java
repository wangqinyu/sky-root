package com.sky.skyserver.util.qrcode.wrapper;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 探测图形的配置信息
 */
public class DetectOptions {
    private Color outColor;

    private Color inColor;

    /**
     * 探测图形，优先级高于颜色的定制（即存在图片时，用图片绘制探测图形）
     */
    private BufferedImage detectImg;

    public Color getOutColor() {
        return outColor;
    }

    public void setOutColor(Color outColor) {
        this.outColor = outColor;
    }

    public Color getInColor() {
        return inColor;
    }

    public void setInColor(Color inColor) {
        this.inColor = inColor;
    }

    public BufferedImage getDetectImg() {
        return detectImg;
    }

    public void setDetectImg(BufferedImage detectImg) {
        this.detectImg = detectImg;
    }
}
