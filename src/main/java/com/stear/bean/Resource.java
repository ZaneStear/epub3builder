package com.stear.bean;

import com.stear.service.MediaTypeService;
import com.stear.util.StringUtil;
import lombok.Getter;

import java.io.File;

@Getter
public class Resource {
    private final File file;
    private final String id;
    private final String extension;
    private final String mediaType;

    public Resource(File file) {
        this.file = file;
        id = file.getName();
        extension = StringUtil.getFileExtension(id);
        mediaType = MediaTypeService.getMediaTypeType(extension);
    }

}
