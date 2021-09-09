package com.stear.service;

import com.stear.bean.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * 此类罗列程序所支持的资源文件类型
 */
public class MediaTypeService {

    public static final Map<String, String> mediaType = new HashMap<>();
    public static final String xhtml = "application/xhtml+xml";
    public static final String jpg = "image/jpg";
    public static final String jpeg = "image/jpeg";
    public static final String png = "image/png";
    public static final String js = "application/javascript";
    public static final String css = "text/css";

    static {
        mediaType.put("xhtml", "application/xhtml+xml");

        mediaType.put("jepg", "image/jpeg");
        mediaType.put("jpg", "image/jpg");
        mediaType.put("png", "image/png");

        mediaType.put("js", "application/javascript");
        mediaType.put("css", "text/css");
    }

    public static String getMediaTypeType(String extension) {
        return mediaType.get(extension);
    }

    public static boolean isImage(Resource resource) {
        String type = resource.getMediaType();
        return type.equals(mediaType.get("jepg")) ||
                type.equals(mediaType.get("jpg")) ||
                type.equals(mediaType.get("png"));
    }
}
