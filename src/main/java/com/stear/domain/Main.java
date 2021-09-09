package com.stear.domain;

import com.stear.bean.Book;
import com.stear.bean.Chapter;
import com.stear.bean.Resource;
import com.stear.util.EPUBWriter;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Book book = new Book("FirstBook");
        book.addChapter(new Chapter("chapterOne", new File("D:\\JavaWorkSpace\\t\\chapter1.xhtml")));
        book.addResource(new Resource(new File("D:\\JavaWorkSpace\\t\\main.css")));

        new EPUBWriter("D:\\JavaWorkSpace\\t\\book", book.getProfessor()).write();

    }
}
