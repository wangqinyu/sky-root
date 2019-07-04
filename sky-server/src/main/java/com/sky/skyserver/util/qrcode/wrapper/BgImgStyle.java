package com.sky.skyserver.util.qrcode.wrapper;

/**
 * 背景图样式
 */
public enum BgImgStyle {
    /**
     * 设置二维码透明度，然后全覆盖背景图
     */
    OVERRIDE,

    /**
     * 将二维码填充在背景图的指定位置
     */
    FILL,

    /**
     * 背景图穿透显示, 即二维码主题色为透明，由背景图的颜色进行填充
     */
    PENETRATE;

    public static BgImgStyle getStyle(String name) {
        return "fill".equalsIgnoreCase(name) ? FILL : OVERRIDE;
    }


}
