package com.sky.skyserver.util.qrcode.wrapper;

import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * 绘制二维码信息的样式
 */
public enum DrawStyle {
    RECT { // 矩形

        @Override
        public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img) {
            g2d.fillRect(x, y, w, h);
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return true;
        }
    }, CIRCLE { // 圆点

        @Override
        public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img) {
            g2d.fill(new Ellipse2D.Float(x, y, w, h));
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return expandType == ExpandType.SIZE4;
        }
    }, TRIANGLE { // 三角形

        @Override
        public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img) {
            int px[] = {x, x + (w >> 1), x + w};
            int py[] = {y + w, y, y + w};
            g2d.fillPolygon(px, py, 3);
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return false;
        }
    }, DIAMOND { // 五边形-钻石

        @Override
        public void draw(Graphics2D g2d, int x, int y, int size, int h, BufferedImage img) {
            int cell4 = size >> 2;
            int cell2 = size >> 1;
            int px[] = {x + cell4, x + size - cell4, x + size, x + cell2, x};
            int py[] = {y, y, y + cell2, y + size, y + cell2};
            g2d.fillPolygon(px, py, 5);
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return expandType == ExpandType.SIZE4;
        }
    }, SEXANGLE { // 六边形

        @Override
        public void draw(Graphics2D g2d, int x, int y, int size, int h, BufferedImage img) {
            int add = size >> 2;
            int px[] = {x + add, x + size - add, x + size, x + size - add, x + add, x};
            int py[] = {y, y, y + add + add, y + size, y + size, y + add + add};
            g2d.fillPolygon(px, py, 6);
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return expandType == ExpandType.SIZE4;
        }
    }, OCTAGON { // 八边形

        @Override
        public void draw(Graphics2D g2d, int x, int y, int size, int h, BufferedImage img) {
            int add = size / 3;
            int px[] = {x + add, x + size - add, x + size, x + size, x + size - add, x + add, x, x};
            int py[] = {y, y, y + add, y + size - add, y + size, y + size, y + size - add, y + add};
            g2d.fillPolygon(px, py, 8);
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return expandType == ExpandType.SIZE4;
        }
    }, IMAGE { // 自定义图片

        @Override
        public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img) {
            g2d.drawImage(img, x, y, w, h, null);
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return true;
        }
    },
    ;

    private static Map<String, DrawStyle> map;

    static {
        map = new HashMap<>(10);
        for (DrawStyle style : DrawStyle.values()) {
            map.put(style.name(), style);
        }
    }

    public static DrawStyle getDrawStyle(String name) {
        if (StringUtils.isBlank(name)) { // 默认返回矩形
            return RECT;
        }


        DrawStyle style = map.get(name.toUpperCase());
        return style == null ? RECT : style;
    }


    public abstract void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img);


    /**
     * 返回是否支持绘制自定义图形的扩展
     *
     * @param expandType
     * @return
     */
    public abstract boolean expand(ExpandType expandType);
}
