package com.sky.skyentity.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="QRCodeParams公共入参", description="QRCodeParams公共入参")
public class QRCodeParams {
    @ApiModelProperty(value = "二维码内容")
    private String content;
    @ApiModelProperty(value = "尺寸：如500")
    private Integer size;
    @ApiModelProperty(value = "背景颜色(16进制0x表示，前两位透明度)" )
    private String bgColor;
    @ApiModelProperty(value = "前景颜色(16进制0x表示，前两位透明度)")
    private String preColor;
    @ApiModelProperty(value = "样式形状(RECT：矩形，CIRCLE：圆点，TRIANGLE：三角形，DIAMOND：五边形，SEXANGLE：六边形，OCTAGON：八边形)")
    private String style;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getPreColor() {
        return preColor;
    }

    public void setPreColor(String preColor) {
        this.preColor = preColor;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
