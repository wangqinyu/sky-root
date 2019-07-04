package com.sky.skyserver.util.qrcode.util;

import java.awt.*;

public class ColorUtil {

    //黑色
    public static Color BLACK = ColorUtil.int2color(0xFF000000);
    //白色
    public static Color WHITE = ColorUtil.int2color(0xFFFFFFFF);
    //红色
    public static Color RED = ColorUtil.int2color(0xFFFF0000);
    //蓝色
    public static Color BLUE = ColorUtil.int2color(0xFF0000FF);
    //绿色
    public static Color GREEN = ColorUtil.int2color(0xFF008000);
    //黄色
    public static Color YELLOW = ColorUtil.int2color(0xFFFFFF00);
    //灰色
    public static Color GRAY = ColorUtil.int2color(0xFF808080);
    //紫色
    public static Color PURPLE = ColorUtil.int2color(0xFF800080);
    //米黄色
    public static Color OFF_WHITE = ColorUtil.int2color(0xFFF7EED6);
    //藏青色
    public static Color NAVY = ColorUtil.int2color(0xFF000080);
    //橄榄色
    public static Color OLIVE = ColorUtil.int2color(0xFF808000);
    //紫红色
    public static Color FUCHSIA = ColorUtil.int2color(0xFFFF00FF);
    //栗色
    public static Color MAROON = ColorUtil.int2color(0xFF800000);
    //凫蓝
    public static Color TEAL = ColorUtil.int2color(0xFF008080);
    //银色
    public static Color SILVER = ColorUtil.int2color(0xFFC0C0C0);
    //橙色
    public static Color ORANGE = ColorUtil.int2color(0xFFFFA500);
    //浅绿色
    public static Color AQUA = ColorUtil.int2color(0xFF00FFFF);
    //金色
    public static Color GOLD = ColorUtil.int2color(0xFFFFD700);
    //天蓝色
    public static Color LIGHTSKYBLUE = ColorUtil.int2color(0xFF87CEFA);
    //粉红色
    public static Color PINK = ColorUtil.int2color(0xFFFFC0CB);
    //青柠色
    public static Color LIME = ColorUtil.int2color(0xFF00FF00);
    //全透明颜色
    public static Color OPACITY = ColorUtil.int2color(0x00FFFFFF);


    /**
     * int格式（16进制）的颜色转为 awt 的Color对象
     *
     * @param color 0xffffffff  前两位是标记位，三四位为透明度， 五六位表示 R， 七八位表示 G， 九十位表示 B
     * 其中透明度参照表；
     * 00%=FF（不透明）    5%=F2    10%=E5    15%=D8    20%=CC    25%=BF    30%=B2    35%=A5    40%=99    45%=8c    50%=7F
     * 55%=72    60%=66    65%=59    70%=4c    75%=3F    80%=33    85%=21    90%=19    95%=0c    100%=00（全透明）
     * @return
     */
    public static Color int2color(int color) {
        int a = ((0x7f000000 & color) >> 24) | 0x00000080;
        int r = (0x00ff0000 & color) >> 16;
        int g = (0x0000ff00 & color) >> 8;
        int b = (0x000000ff & color);
        return new Color(r, g, b, a);
    }

    /**
     * 将Color对象转为html对应的颜色配置信息
     *
     * 如  Color.RED  ->  #f00
     *
     * @param color
     * @return
     */
    public static String int2htmlColor(int color) {
        int r = (0x00ff0000 & color) >> 16;
        int g = (0x0000ff00 & color) >> 8;
        int b = (0x000000ff & color);
        return "#" + NumUtil.toHex(r) + NumUtil.toHex(g) + NumUtil.toHex(b);
    }
}
