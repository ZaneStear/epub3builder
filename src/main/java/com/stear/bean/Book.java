package com.stear.bean;

import com.stear.service.TimeService;
import com.stear.util.BookProcessor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Book {
    private final String bookTitle;
    private String creator;
    private String description;
    private String bookID;
    private String pubID;
    private String language;
    private String rights;
    private String modifiedData;
    private List<String> contributor;
    private Resource coverImage;

    @Setter(AccessLevel.NONE)
    private List<Chapter> chapterList;
    //记录章节名，防止章节名重复
    private List<String> chapterTitleList;

    @Setter(AccessLevel.NONE)
    private List<Resource> resourceList;
    //记录资源ID，防止ID重复
    private List<String> resourceIDList;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private BookProcessor professor;

    /**
     * 基本初始化
     *
     * @param bookTitle
     * @param bookID
     * @param pubID
     * @param language
     */
    public Book(String bookTitle, String bookID, String pubID, String language, String modifiedData) {
        this.bookTitle = bookTitle;
        this.bookID = bookID;
        this.pubID = pubID;
        this.language = language;
        this.modifiedData = modifiedData;
        contributor = new ArrayList<>();
        chapterList = new ArrayList<>();
        resourceList = new ArrayList<>();
        chapterTitleList = new ArrayList<>();
        resourceIDList = new ArrayList<>();
    }

    /**
     * 默认初始化方法
     *
     * @param bookTitle
     */
    public Book(String bookTitle) {
        this(bookTitle, "DefaultBookID", "DefaultPubID", "zh", TimeService.getSystemUTCTime());
    }

    public BookProcessor getProfessor() {
        return new BookProcessor(this);
    }

    @SneakyThrows
    public void addChapter(Chapter chapter) {
        if (chapterTitleList.contains(chapter.getChapterTitle())) {
            throw new Exception("Duplicate ChapterTitle:" + chapter.getChapterTitle());
        }
        chapterList.add(chapter);
        chapterTitleList.add(chapter.getChapterTitle());
    }

    @SneakyThrows
    public void addResource(Resource resource) {
        if (resourceIDList.contains(resource.getId())) {
            throw new Exception("Duplicate ResourceID:" + resource.getId());
        }
        resourceList.add(resource);
        resourceIDList.add(resource.getId());
    }
}
