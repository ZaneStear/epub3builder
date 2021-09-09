package com.stear.util;

import com.stear.service.MediaTypeService;
import lombok.Getter;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

@Getter
public class PackageOPF extends ConfigXML {
    private Document document;
    private Element rootPackage;
    private Element metadata;
    private Element manifest;
    private Element spine;

    /**
     * 无参构造方法，进行不完全的标签建立，缺少必要的id，language
     */
    public PackageOPF() {
        document = DocumentHelper.createDocument();
        //初始化package标签属性.版本，三个命名空间。
        rootPackage = document.addElement("package", "http://www.idpf.org/2007/opf")
                .addNamespace("dc", "http://purl.org/dc/elements/1.1/")
                .addNamespace("dcterms", "http://purl.org/dc/terms/")
                .addAttribute("version", "3.0");

        //建立metadata标签
        metadata = rootPackage.addElement("metadata");
        //建立manifest标签
        manifest = rootPackage.addElement("manifest");
        //建立spine标签
        spine = rootPackage.addElement("spine");
    }

    public void addTOCItemToManifest(String href) {
        Element toc = addItemToManifest("toc", href, MediaTypeService.getMediaTypeType("xhtml"));
        addAttribute(toc, "properties", "nav");
    }

    public Element addItemToManifest(String id, String href, String mediaType) {
        return addItemToManifest(manifest, id, href, mediaType);
    }

    public void addItemRefToSpine(String id) {
        Element itemref = addTag(spine, "itemref");
        addAttribute(itemref, "idref", id);
    }

    /**
     * 设置ID
     *
     * @param bookID
     * @param pubID
     */
    public void setID(String bookID, String pubID) {
        addAttribute(rootPackage, "unique-identifier", bookID);
        Element identifier = addTag(metadata, "dc:identifier", pubID);
        addAttribute(identifier, "id", bookID);
    }

    public void setCoverImage(String ID, String href, String mediaType) {
        Element coverItem = addItemToManifest(manifest, ID, href, mediaType);
        addAttribute(coverItem, "properties", "cover-image");
    }

    public void setBookTitle(String bookTitle) {
        //初始化title
        addTag(metadata, "dc:title", bookTitle);
    }

    public void setLanguage(String language) {
        //初始化语言
        addTag(metadata, "dc:language", language);
    }

    public void setModifiedData(String modifiedData) {
        Element meta = addTag(metadata, "meta", modifiedData);
        addAttribute(meta, "property", "dcterms:modified");
    }

    public void setCreator(String creator) {
        addTag(metadata, "dc:creator", creator);
    }

    public void addContributor(String contributor) {
        addTag(metadata, "dc:contributor", contributor);
    }

    public void addRights(String rights) {
        addTag(metadata, "dc:rights", rights);
    }

    public void addData(String data) {
        addTag(metadata, "dc:data", data);
    }
}
