package com.sky.skyserver.util.qrcode.wrapper;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sky.skyserver.util.qrcode.helper.QrCodeHelper;
import com.sky.skyserver.util.qrcode.util.ImageLoadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.sky.skyserver.util.qrcode.util.ColorUtil.int2color;

public class QrCodeGenWrapper {
    static Logger logger = LoggerFactory.getLogger(QrCodeGenWrapper.class);
    public static Builder of(String content) {
        return new Builder().setMsg(content);
    }
    private static BufferedImage asBufferedImage(QrCodeOptions qrCodeConfig) throws WriterException, IOException {
        BitMatrixEx bitMatrix = QrCodeHelper.encode(qrCodeConfig);
        return QrCodeHelper.toBufferedImage(qrCodeConfig, bitMatrix);
    }

    private static String asString(QrCodeOptions qrCodeOptions) throws WriterException, IOException {
        BufferedImage bufferedImage = asBufferedImage(qrCodeOptions);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, qrCodeOptions.getPicType(), outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

//    private static boolean asFile(QrCodeOptions qrCodeConfig, String absFileName) throws WriterException, IOException {
//        File file = new File(absFileName);
//
//        FileWriteUtil.mkDir(file);
//
//        BufferedImage bufferedImage = asBufferedImage(qrCodeConfig);
//        if (!ImageIO.write(bufferedImage, qrCodeConfig.getPicType(), file)) {
//            throw new IOException("save QrCode image to: " + absFileName + " error!");
//        }
//
//        return true;
//    }

    public static class Builder {
        private static final MatrixToImageConfig DEFAULT_CONFIG = new MatrixToImageConfig();

        /**
         * The message to put into QrCode
         */
        private String msg;

        /**
         * qrcode image width
         */
        private Integer w;


        /**
         * qrcode image height
         */
        private Integer h;


        /**
         * qrcode message's code, default UTF-8
         */
        private String code = "utf-8";


        /**
         * 0 - 4
         */
        private Integer padding;


        /**
         * error level, default H
         */
        private ErrorCorrectionLevel errorCorrection = ErrorCorrectionLevel.H;


        /**
         * output qrcode image type, default png
         */
        private String picType = "png";


        private BgImgOptions bgImgOptions;

        private LogoOptions logoOptions;

        private DrawOptions drawOptions;

        private DetectOptions detectOptions;


        public Builder() {
            // 背景图默认采用覆盖方式
            bgImgOptions = new BgImgOptions();
            bgImgOptions.setBgImgStyle(BgImgStyle.OVERRIDE);
            bgImgOptions.setOpacity(0.85f);

            // 默认采用普通格式的logo， 无边框
            logoOptions = new LogoOptions();
            logoOptions.setLogoStyle(LogoStyle.NORMAL);
            logoOptions.setBorder(false);
            logoOptions.setRate(12);

            // 绘制信息，默认黑白方块
            drawOptions = new DrawOptions();
            drawOptions.setDrawStyle(DrawStyle.RECT);
            drawOptions.setBgColor(Color.WHITE);
            drawOptions.setPreColor(Color.BLACK);
            drawOptions.setEnableScale(false);

            // 探测图形
            detectOptions = new DetectOptions();
        }


        public String getMsg() {
            return msg;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Integer getW() {
            return w == null ? (h == null ? 200 : h) : w;
        }

        public Builder setW(Integer w) {
            if (w != null && w <= 0) {
                throw new IllegalArgumentException("生成二维码的宽必须大于0");
            }
            this.w = w;
            return this;
        }

        public Integer getH() {
            return h == null ? (w == null ? 200 : w) : h;
        }

        public Builder setH(Integer h) {
            if (h != null && h <= 0) {
                throw new IllegalArgumentException("生成功能二维码的搞必须大于0");
            }
            this.h = h;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Integer getPadding() {
            if (padding == null) {
                return 1;
            }

            if (padding < 0) {
                return 0;
            }

            if (padding > 4) {
                return 4;
            }

            return padding;
        }

        public Builder setPadding(Integer padding) {
            this.padding = padding;
            return this;
        }

        public Builder setPicType(String picType) {
            this.picType = picType;
            return this;
        }

        public Builder setErrorCorrection(ErrorCorrectionLevel errorCorrection) {
            this.errorCorrection = errorCorrection;
            return this;
        }


        /////////////// logo 相关配置 ///////////////

        public Builder setLogo(String logo) throws IOException {
            try {
                return setLogo(ImageLoadUtil.getImageByPath(logo));
            } catch (IOException e) {
                logger.error("load logo error! e:{}", e);
                throw new IOException("load logo error!", e);
            }
        }

        public Builder setLogo(InputStream inputStream) throws IOException {
            try {
                return setLogo(ImageIO.read(inputStream));
            } catch (IOException e) {
                logger.error("load backgroundImg error! e:{}", e);
                throw new IOException("load backgroundImg error!", e);
            }
        }

        public Builder setLogo(BufferedImage img) {
            logoOptions.setLogo(img);
            return this;
        }

        public Builder setLogoStyle(LogoStyle logoStyle) {
            logoOptions.setLogoStyle(logoStyle);
            return this;
        }

        public Builder setLogoBgColor(Integer color) {
            if (color == null) {
                return this;
            }

            return setLogoBgColor(int2color(color));
        }

        public Builder setLogoBgColor(Color color) {
            logoOptions.setBorder(true);
            logoOptions.setBorderColor(color);
            return this;
        }

        public Builder setLogoBorder(boolean border) {
            logoOptions.setBorder(border);
            return this;
        }

        public Builder setLogoRate(int rate) {
            logoOptions.setRate(rate);
            return this;
        }

        ///////////////// logo配置结束 ///////////////


        // ------------------------------------------


        /////////////// 背景 相关配置 ///////////////

        public Builder setBgImg(String bgImg) throws IOException {
            try {
                return setBgImg(ImageLoadUtil.getImageByPath(bgImg));
            } catch (IOException e) {
                logger.error("load backgroundImg error! e:{}", e);
                throw new IOException("load backgroundImg error!", e);
            }
        }


        public Builder setBgImg(InputStream inputStream) throws IOException {
            try {
                return setBgImg(ImageIO.read(inputStream));
            } catch (IOException e) {
                logger.error("load backgroundImg error! e:{}", e);
                throw new IOException("load backgroundImg error!", e);
            }
        }


        public Builder setBgImg(BufferedImage bufferedImage) {
            bgImgOptions.setBgImg(bufferedImage);
            return this;
        }


        public Builder setBgStyle(BgImgStyle bgImgStyle) {
            bgImgOptions.setBgImgStyle(bgImgStyle);
            return this;
        }


        public Builder setBgW(int w) {
            bgImgOptions.setBgW(w);
            return this;
        }

        public Builder setBgH(int h) {
            bgImgOptions.setBgH(h);
            return this;
        }


        public Builder setBgOpacity(float opacity) {
            bgImgOptions.setOpacity(opacity);
            return this;
        }


        public Builder setBgStartX(int startX) {
            bgImgOptions.setStartX(startX);
            return this;
        }

        public Builder setBgStartY(int startY) {
            bgImgOptions.setStartY(startY);
            return this;
        }


        /////////////// logo 配置结束 ///////////////


        // ------------------------------------------


        /////////////// 探测图形 相关配置 ///////////////
        public Builder setDetectImg(String detectImg) throws IOException {
            try {
                return setDetectImg(ImageLoadUtil.getImageByPath(detectImg));
            } catch (IOException e) {
                logger.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }


        public Builder setDetectImg(InputStream detectImg) throws IOException {
            try {
                return setDetectImg(ImageIO.read(detectImg));
            } catch (IOException e) {
                logger.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }


        public Builder setDetectImg(BufferedImage detectImg) {
            detectOptions.setDetectImg(detectImg);
            return this;
        }


        public Builder setDetectOutColor(Integer outColor) {
            if (outColor == null) {
                return this;
            }

            return setDetectOutColor(int2color(outColor));
        }

        public Builder setDetectOutColor(Color outColor) {
            detectOptions.setOutColor(outColor);
            return this;
        }

        public Builder setDetectInColor(Integer inColor) {
            if (inColor == null) {
                return this;
            }

            return setDetectInColor(int2color(inColor));
        }

        public Builder setDetectInColor(Color inColor) {
            detectOptions.setInColor(inColor);
            return this;
        }

        /////////////// 探测图形 配置结束 ///////////////


        // ------------------------------------------


        /////////////// 二维码绘制 相关配置 ///////////////

        public Builder setDrawStyle(String style) {
            return setDrawStyle(DrawStyle.getDrawStyle(style));
        }


        public Builder setDrawStyle(DrawStyle drawStyle) {
            drawOptions.setDrawStyle(drawStyle);
            return this;
        }


        public Builder setDrawPreColor(int color) {
            return setDrawPreColor(int2color(color));
        }


        public Builder setDrawPreColor(Color color) {
            drawOptions.setPreColor(color);
            return this;
        }

        public Builder setDrawBgColor(int color) {
            return setDrawBgColor(int2color(color));
        }

        public Builder setDrawBgColor(Color color) {
            drawOptions.setBgColor(color);
            return this;
        }

        public Builder setDrawEnableScale(boolean enable) {
            drawOptions.setEnableScale(enable);
            return this;
        }


        public Builder setDrawImg(String img) throws IOException {
            try {
                return setDrawImg(ImageLoadUtil.getImageByPath(img));
            } catch (IOException e) {
                logger.error("load draw img error! e: {}", e);
                throw new IOException("load draw img error!", e);
            }
        }

        public Builder setDrawImg(InputStream input) throws IOException {
            try {
                return setDrawImg(ImageIO.read(input));
            } catch (IOException e) {
                logger.error("load draw img error! e: {}", e);
                throw new IOException("load draw img error!", e);
            }
        }

        public Builder setDrawImg(BufferedImage img) {
            addImg(1, 1, img);
            return this;
        }


        public Builder addImg(int row, int col, BufferedImage img) {
            if (img == null) {
                return this;
            }
            drawOptions.setEnableScale(true);
            drawOptions.drawImg(row, col, img);
            return this;
        }

        public Builder addImg(int row, int col, String img) throws IOException {
            try {
                return addImg(row, col, ImageLoadUtil.getImageByPath(img));
            } catch (IOException e) {
                logger.error("load draw size4img error! e: {}", e);
                throw new IOException("load draw row:" + row + ", col:" + col + " img error!", e);
            }
        }

        public Builder addImg(int row, int col, InputStream img) throws IOException {
            try {
                return addImg(row, col, ImageIO.read(img));
            } catch (IOException e) {
                logger.error("load draw size4img error! e: {}", e);
                throw new IOException("load draw row:" + row + ", col:" + col + " img error!", e);
            }
        }

        /////////////// 二维码绘制 配置结束 ///////////////


        private void validate() {
            if (msg == null || msg.length() == 0) {
                throw new IllegalArgumentException("生成二维码的内容不能为空!");
            }
        }


        private QrCodeOptions build() {
            this.validate();

            QrCodeOptions qrCodeConfig = new QrCodeOptions();
            qrCodeConfig.setMsg(getMsg());
            qrCodeConfig.setH(getH());
            qrCodeConfig.setW(getW());


            // 设置背景信息
            BgImgOptions bgOp = new BgImgOptions();
            if (bgOp.getBgImg() == null) {
                qrCodeConfig.setBgImgOptions(null);
            } else {
                qrCodeConfig.setBgImgOptions(bgOp);
            }


            // 设置logo信息
            LogoOptions logoOp = new LogoOptions();
            if (logoOp.getLogo() == null) {
                qrCodeConfig.setLogoOptions(null);
            } else {
                qrCodeConfig.setLogoOptions(logoOp);
            }


            // 绘制信息
            DrawOptions drawOp = drawOptions.build();
            qrCodeConfig.setDrawOptions(drawOp);


            // 设置detect绘制信息
            DetectOptions detectOp = new DetectOptions();
            if (detectOp.getOutColor() == null && detectOp.getInColor() == null) {
                detectOp.setInColor(drawOp.getPreColor());
                detectOp.setOutColor(drawOp.getPreColor());
            } else if (detectOp.getOutColor() == null) {
                detectOp.setOutColor(detectOp.getOutColor());
            } else if (detectOp.getInColor() == null) {
                detectOp.setInColor(detectOp.getInColor());
            }
            qrCodeConfig.setDetectOptions(detectOp);


            // 设置输出图片格式
            qrCodeConfig.setPicType(picType);

            // 设置精度参数
            Map<EncodeHintType, Object> hints = new HashMap<>(3);
            hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrection);
            hints.put(EncodeHintType.CHARACTER_SET, code);
            hints.put(EncodeHintType.MARGIN, this.getPadding());
            qrCodeConfig.setHints(hints);

            return qrCodeConfig;
        }


        public String asString() throws IOException, WriterException {
            return QrCodeGenWrapper.asString(build());
        }


        public BufferedImage asBufferedImage() throws IOException, WriterException {
            return QrCodeGenWrapper.asBufferedImage(build());
        }


        /*public boolean asFile(String absFileName) throws IOException, WriterException {
            return QrCodeGenWrapper.asFile(build(), absFileName);
        }*/
    }
}
