package com.sky.skyserver.util.qrcode.util;

import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileReadUtil {

    public static String readAll(String fileName) throws IOException {
        BufferedReader reader = createLineRead(fileName);
        List<String> lines = reader.lines().collect(Collectors.toList());
        return Joiner.on("\n").join(lines);
    }


    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     *
     * @param fileName 文件的名
     */
    public static InputStream createByteRead(String fileName) throws IOException {
        return getStreamByFileName(fileName);
    }


    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     *
     * @param fileName 文件名
     */
    public static Reader createCharRead(String fileName) throws IOException {
        return new InputStreamReader(getStreamByFileName(fileName), Charset.forName("UTF-8"));
    }


    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     *
     * @param fileName 文件名
     */
    public static BufferedReader createLineRead(String fileName) throws IOException {
        return new BufferedReader(new InputStreamReader(getStreamByFileName(fileName), Charset.forName("UTF-8")));
    }



    public static InputStream getStreamByFileName(String fileName) throws IOException {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName should not be null!");
        }

        if (isAbsFile(fileName)) {
            // 绝对路径
            Path path = Paths.get(fileName);
            return Files.newInputStream(path);
        } else if (fileName.startsWith("~")) {
            // 用户目录下的绝对路径文件
            fileName = parseHomeDir2AbsDir(fileName);
            return Files.newInputStream(Paths.get(fileName));
        } else { // 相对路径
            return FileReadUtil.class.getClassLoader().getResourceAsStream(fileName);
        }
    }

    /** 将字节数组转换成16进制字符串 */
    private static String bytesToHex(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }


        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * 获取文件对应的魔数
     * @param file
     * @return
     */
    public static String getMagicNum(String file) {
        try (InputStream stream = FileReadUtil.getStreamByFileName(file)) {

            byte[] b = new byte[28];
            stream.read(b, 0, 28);

            return bytesToHex(b);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isAbsFile(String fileName) {
        if (isWinOS()) {
            // windows 操作系统时，绝对地址形如  c:\descktop
            return fileName.contains(":") || fileName.startsWith("\\");
        } else {
            // mac or linux
            return fileName.startsWith("/");
        }
    }

    /**
     * 将用户目录下地址~/xxx 转换为绝对地址
     *
     * @param path
     * @return
     */
    public static String parseHomeDir2AbsDir(String path) {
        String homeDir = System.getProperties().getProperty("user.home");
        return StringUtils.replace(path, "~", homeDir);
    }


    /**
     * 是否windows系统
     */
    public static boolean isWinOS() {
        boolean isWinOS = false;
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            String sharpOsName = osName.replaceAll("windows", "{windows}").replaceAll("^win([^a-z])", "{windows}$1")
                    .replaceAll("([^a-z])win([^a-z])", "$1{windows}$2");
            isWinOS = sharpOsName.contains("{windows}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isWinOS;
    }
}
