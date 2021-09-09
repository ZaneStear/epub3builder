package com.stear.bean;

import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Chapter {
    private final String chapterTitle;
    private final File htmlFile;
    private final String chapterID;
    private final List<Chapter> chapterList;

    public Chapter(String chapterTitle, File htmlFile) {
        this.chapterTitle = chapterTitle;
        this.htmlFile = htmlFile;
        this.chapterID = htmlFile.getName();
        chapterList = new ArrayList<>();
    }

    public Chapter addChapter(Chapter chapter) {
        chapterList.add(chapter);
        return this;
    }
}
