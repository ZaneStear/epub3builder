package com.stear.service;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class PathNameService {
    private String metaInfPathName = "META-INF";
    private String epubPathName = "EPUB";
    private String htmlPathName = "html";
    private String imagePathName = "image";
    private String cssPathName = "css";
    private String jsPathName = "js";
    private String packageOPFName = "package";
    private String tocName = "toc";

    private Map<String, String> resourcePathName = new HashMap<>();

    public PathNameService() {
        resourcePathName.put("html", htmlPathName);
        resourcePathName.put("jepg", imagePathName);
        resourcePathName.put("jpg", imagePathName);
        resourcePathName.put("png", imagePathName);
        resourcePathName.put("css", cssPathName);
        resourcePathName.put("js", jsPathName);
    }


    public String getPathName(String extension) {
        return resourcePathName.get(extension);
    }
}
