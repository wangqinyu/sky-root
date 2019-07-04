package com.sky.skyserver.util.qrcode.wrapper;

import java.awt.image.BufferedImage;

/**
 * 背景图的配置信息
 */
public class BgImgOptions {
    /**
     * 背景图
     */
    private BufferedImage bgImg;

    /**
     * 背景图宽
     */
    private int bgW;

    /**
     * 背景图高
     */
    private int bgH;

    /**
     * 背景图样式
     */
    private BgImgStyle bgImgStyle;

    /**
     * if {@link #bgImgStyle} ==  QrCodeOptions.BgImgStyle.OVERRIDE，
     * 用于设置二维码的透明度
     */
    private float opacity;


    /**
     * if {@link #bgImgStyle} ==  QrCodeOptions.BgImgStyle.FILL
     * <p>
     * 用于设置二维码的绘制在背景图上的x坐标
     */
    private int startX;


    /**
     * if {@link #bgImgStyle} ==  QrCodeOptions.BgImgStyle.FILL
     * <p>
     * 用于设置二维码的绘制在背景图上的y坐标
     */
    private int startY;


    public int getBgW() {
        if (bgW == 0 && bgImg != null && bgImgStyle == BgImgStyle.FILL) {
            return bgImg.getWidth();
        }
        return bgW;
    }

    public int getBgH() {
        if (bgH == 0 && bgImg != null && bgImgStyle == BgImgStyle.FILL) {
            return bgImg.getHeight();
        }
        return bgH;
    }

    public BufferedImage getBgImg() {
        return bgImg;
    }

    public void setBgImg(BufferedImage bgImg) {
        this.bgImg = bgImg;
    }

    public void setBgW(int bgW) {
        this.bgW = bgW;
    }

    public void setBgH(int bgH) {
        this.bgH = bgH;
    }

    public BgImgStyle getBgImgStyle() {
        return bgImgStyle;
    }

    public void setBgImgStyle(BgImgStyle bgImgStyle) {
        this.bgImgStyle = bgImgStyle;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
}