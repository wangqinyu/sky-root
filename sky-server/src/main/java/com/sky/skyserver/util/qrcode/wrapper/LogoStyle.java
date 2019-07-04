package com.sky.skyserver.util.qrcode.wrapper;

/**
 * logo的样式
 */
public enum LogoStyle {
    ROUND, NORMAL;
    public static LogoStyle getStyle(String name) {
        return "ROUND".equalsIgnoreCase(name) ? ROUND : NORMAL;
    }
}