package com.stear.util;

public class StringUtil {
    /**
     * 返回文件扩展名，如.jpg
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

}
