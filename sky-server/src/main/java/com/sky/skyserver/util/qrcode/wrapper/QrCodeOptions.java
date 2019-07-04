package com.sky.skyserver.util.qrcode.wrapper;

import com.google.zxing.EncodeHintType;

import java.util.Map;

public class QrCodeOptions {
    //塞入二维码的信息
    private String msg;
    //生成二维码的宽
    private Integer w;
    //生成二维码的高
    private Integer h;
    //二维码信息(即传统二维码中的黑色方块) 绘制选项
    private DrawOptions drawOptions;
    //背景图样式选项
    private BgImgOptions bgImgOptions;
    //logo 样式选项
    private LogoOptions logoOptions;

    //三个探测图形的样式选项(后续可以考虑三个都可以自配置)
    private DetectOptions detectOptions;
    private Map<EncodeHintType, Object> hints;
    //生成二维码图片的格式 png, jpg
    private String picType;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public DrawOptions getDrawOptions() {
        return drawOptions;
    }

    public void setDrawOptions(DrawOptions drawOptions) {
        this.drawOptions = drawOptions;
    }

    public BgImgOptions getBgImgOptions() {
        return bgImgOptions;
    }

    public void setBgImgOptions(BgImgOptions bgImgOptions) {
        this.bgImgOptions = bgImgOptions;
    }

    public LogoOptions getLogoOptions() {
        return logoOptions;
    }

    public void setLogoOptions(LogoOptions logoOptions) {
        this.logoOptions = logoOptions;
    }

    public DetectOptions getDetectOptions() {
        return detectOptions;
    }

    public void setDetectOptions(DetectOptions detectOptions) {
        this.detectOptions = detectOptions;
    }

    public Map<EncodeHintType, Object> getHints() {
        return hints;
    }

    public void setHints(Map<EncodeHintType, Object> hints) {
        this.hints = hints;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }
}
