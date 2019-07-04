package com.sky.skyserver.util.qrcode.wrapper;

import com.sky.skyserver.util.qrcode.entity.DotSize;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * 绘制二维码的配置信息
 */
public class DrawOptions {
    /**
     * 着色颜色
     */
    private Color preColor;

    /**
     * 背景颜色
     */
    private Color bgColor;

    /**
     * 绘制样式
     */
    private DrawStyle drawStyle;

    /**
     * true 时表示支持对相邻的着色点进行合并处理 （即用一个大图来绘制相邻的两个着色点）
     * 说明： 三角形样式关闭该选项，因为留白过多，对识别有影响
     */
    private boolean enableScale;

    /**
     * 渲染图
     */
    private Map<DotSize, BufferedImage> imgMapper = new HashMap<>();

    public BufferedImage getImage(int row, int col) {
        DotSize dotSize = DotSize.create(row, col);
        return imgMapper.get(dotSize);
    }

    public void drawImg(int row, int column, BufferedImage image) {
        imgMapper.put(new DotSize(row, column), image);
    }

    public DrawOptions build() {
        DrawOptions drawOptions = new DrawOptions();
        drawOptions.setBgColor(this.bgColor);
        drawOptions.setPreColor(this.preColor);
        drawOptions.setDrawStyle(this.drawStyle);
        drawOptions.setEnableScale(this.enableScale);
        drawOptions.setImgMapper(this.imgMapper);
        return drawOptions;
    }
    public Color getPreColor() {
        return preColor;
    }

    public void setPreColor(Color preColor) {
        this.preColor = preColor;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public DrawStyle getDrawStyle() {
        return drawStyle;
    }

    public void setDrawStyle(DrawStyle drawStyle) {
        this.drawStyle = drawStyle;
    }

    public boolean isEnableScale() {
        return enableScale;
    }

    public void setEnableScale(boolean enableScale) {
        this.enableScale = enableScale;
    }

    public Map<DotSize, BufferedImage> getImgMapper() {
        return imgMapper;
    }

    public void setImgMapper(Map<DotSize, BufferedImage> imgMapper) {
        this.imgMapper = imgMapper;
    }
}
