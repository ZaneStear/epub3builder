package com.stear.util;

import com.stear.bean.Book;
import com.stear.bean.Chapter;
import com.stear.bean.Resource;
import com.stear.service.MediaTypeService;
import com.stear.service.PathNameService;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class EPUBWriter {
    private final String workSpace;
    private final BookProcessor bookProcessor;
    private final Book book;
    private String bookSource;
    private String EPUBPath;
    private String metaInfPath;
    private String htmlPath;
    private String cssPath;
    private String imagePath;
    private String jsPath;
    private PathNameService pathNameService;

    public EPUBWriter(String workSpace, BookProcessor bookProcessor) {
        this.workSpace = workSpace;
        this.book = bookProcessor.getBook();
        this.bookProcessor = bookProcessor.process();
        initPath();
    }

    @SneakyThrows
    public void write() {
        makeDirs();
        //写入epub的固定文件
        Files.writeString(Path.of(bookSource + "\\mimetype"), "application/epub+zip", StandardCharsets.UTF_16);
        Files.writeString(Path.of(metaInfPath + "\\container.xml"), bookProcessor.getContainerXML());
        writeXML(EPUBPath, pathNameService.getPackageOPFName() + ".opf", bookProcessor.getPackageOPF().getDocument());
        writeXML(EPUBPath, pathNameService.getTocName() + ".xhtml", bookProcessor.getToc().getDocument());
        moveResources();
        ZipUtil.unexplode(new File(bookSource));
        File resultBook = new File(bookSource);
        resultBook.renameTo(new File(resultBook + ".epub"));
    }

    @SneakyThrows
    private void moveResources() {
        //移动封面
        if (book.getCoverImage() != null) {
            File coverImage = book.getCoverImage().getFile();
            Files.copy(Path.of(coverImage.getAbsolutePath()), Path.of(imagePath, coverImage.getName()));
        }
        //移动资源
        for (Resource r :
                book.getResourceList()) {
            File src = r.getFile();
            String id = r.getId();
            String currentType = r.getMediaType();
            switch (r.getMediaType()) {
                case MediaTypeService.xhtml:
                    Files.copy(Path.of(src.getAbsolutePath()), Path.of(htmlPath, id));
                    break;
                case MediaTypeService.css:
                    Files.copy(Path.of(src.getAbsolutePath()), Path.of(cssPath, id));
                    break;
                case MediaTypeService.jpeg:
                case MediaTypeService.jpg:
                case MediaTypeService.png:
                    Files.copy(Path.of(src.getAbsolutePath()), Path.of(imagePath, id));
                    break;
                case MediaTypeService.js:
                    Files.copy(Path.of(src.getAbsolutePath()), Path.of(jsPath, id));
                    break;
            }
            //移动章节xhtml
            for (Chapter c :
                    book.getChapterList()) {
                File f = c.getHtmlFile();
                Files.copy(Path.of(f.getAbsolutePath()), Path.of(htmlPath, f.getName()));
            }
        }
    }

    private void makeDirs() {
        new File(metaInfPath).mkdirs();
        new File(htmlPath).mkdirs();
        new File(cssPath).mkdirs();
        new File(imagePath).mkdirs();
        new File(jsPath).mkdirs();
    }

    private void initPath() {
        pathNameService = bookProcessor.getPathNameService();
        bookSource = workSpace + "\\" + book.getBookTitle();

        EPUBPath = bookSource + "\\" + pathNameService.getEpubPathName();
        metaInfPath = bookSource + "\\" + pathNameService.getMetaInfPathName();

        htmlPath = EPUBPath + "\\" + pathNameService.getHtmlPathName();
        cssPath = EPUBPath + "\\" + pathNameService.getCssPathName();
        imagePath = EPUBPath + "\\" + pathNameService.getImagePathName();
        jsPath = EPUBPath + "\\" + pathNameService.getJsPathName();
    }

    @SneakyThrows
    public void writeXML(String path, String name, Document document) {
        File file = new File(path, name);
        OutputStream outputStream = new FileOutputStream(file);
        OutputFormat outputFormat = OutputFormat.createPrettyPrint();
        XMLWriter xmlWriter = new XMLWriter(outputStream, outputFormat);
        xmlWriter.write(document);
        outputStream.close();
    }
}
