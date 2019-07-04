package com.sky.skyserver.util.qrcode.wrapper;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * logo 的配置信息
 */
public class LogoOptions {

    //logo 图片
    private BufferedImage logo;

    //logo 样式
    private LogoStyle logoStyle;

    //logo 占二维码的比例
    private int rate;

    //true 表示有边框，false 表示无边框
    private boolean border;

    //边框颜色
    private Color borderColor;

    public BufferedImage getLogo() {
        return logo;
    }

    public void setLogo(BufferedImage logo) {
        this.logo = logo;
    }

    public LogoStyle getLogoStyle() {
        return logoStyle;
    }

    public void setLogoStyle(LogoStyle logoStyle) {
        this.logoStyle = logoStyle;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public boolean isBorder() {
        return border;
    }

    public void setBorder(boolean border) {
        this.border = border;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
