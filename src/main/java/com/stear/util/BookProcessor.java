package com.stear.util;

import com.stear.bean.Book;
import com.stear.bean.Chapter;
import com.stear.bean.Resource;
import com.stear.service.MediaTypeService;
import com.stear.service.PathNameService;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.List;

@Getter
public class BookProcessor {
    private final Book book;
    private final PathNameService pathNameService;
    private PackageOPF packageOPF;
    private TOC toc;


    public BookProcessor(Book book) {
        this(book, new PathNameService());
    }

    @SneakyThrows
    public BookProcessor(Book book, PathNameService pathNameService) {
        if (book.getBookID() == null || book.getPubID() == null) {
            throw new Exception("BookID or PubID is not allowed to be null");
        }
        this.pathNameService = pathNameService;
        this.book = book;
        packageOPF = new PackageOPF();
        toc = new TOC();
    }

    public BookProcessor process() {
        packageOPF.setID(book.getBookID(), book.getPubID());
        packageOPF.addTOCItemToManifest(pathNameService.getTocName() + ".xhtml");
        packageOPF.setBookTitle(book.getBookTitle());
        packageOPF.setLanguage(book.getLanguage());
        packageOPF.setModifiedData(book.getModifiedData());
        setCoverImage(book.getCoverImage());
        processChapterList(book.getChapterList());
        processResources(book.getResourceList());
        return this;
    }

    @SneakyThrows
    private void processChapterList(List<Chapter> chapterList) {
        for (Chapter c :
                chapterList) {
            String extension = StringUtil.getFileExtension(c.getHtmlFile().getName());
            if (!extension.equals("xhtml")) {
                throw new Exception("Chapter file can only be XHTML file:" + extension);
            }
            String href = pathNameService.getHtmlPathName() + "/" + c.getChapterID();
            packageOPF.addItemToManifest(c.getChapterID(),
                    href,
                    MediaTypeService.getMediaTypeType("xhtml"));
            packageOPF.addItemRefToSpine(c.getChapterID());
            toc.addDefaultLi(href, c.getChapterTitle());
        }
    }


    @SneakyThrows
    private void setCoverImage(Resource coverImage) {
        if (coverImage != null) {
            if (!MediaTypeService.isImage(coverImage)) {
                throw new Exception("Cover Image must be a image type.(jpg,jepg or png)");
            }
            packageOPF.setCoverImage(coverImage.getId(), pathNameService.getImagePathName() + "/" + coverImage.getId(), coverImage.getMediaType());
        }
    }

    private void processResources(List<Resource> resourceList) {
        for (Resource r : resourceList) {
            packageOPF.addItemToManifest(r.getId(), pathNameService.getPathName(r.getExtension()) + "/" + r.getId(), r.getMediaType());
        }
    }

    public String getContainerXML() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\n" +
                "<container xmlns=\"urn:oasis:names:tc:opendocument:xmlns:container\" version=\"1.0\">\n" +
                "\t<rootfiles>\n" +
                "\t\t<rootfile full-path=\"" + pathNameService.getEpubPathName() + "/" + pathNameService.getPackageOPFName() + ".opf\" media-type=\"application/oebps-package+xml\"/>\n" +
                "\t</rootfiles>\n" +
                "</container>";
    }


}
