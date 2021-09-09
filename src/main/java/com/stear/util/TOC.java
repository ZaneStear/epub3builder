package com.stear.util;

import lombok.Getter;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

@Getter
public class TOC extends ConfigXML {
    private Document document;
    private Element rootHtml;
    private Element body;
    private Element head;
    private Element nav;
    private Element rootOl;

    public TOC() {
        document = DocumentHelper.createDocument();
        rootHtml = document.addElement("html", "http://www.w3.org/1999/xhtml")
                .addNamespace("epub", "http://www.idpf.org/2007/ops");

        head = rootHtml.addElement("head");
        head.addElement("title").addText("title");

        body = rootHtml.addElement("body");
        nav = body.addElement("nav")
                .addAttribute("epub:type", "toc");

        rootOl = nav.addElement("ol");
    }

    public void addDefaultLi(String href, String title) {
        Element li = rootOl.addElement("li");
        Element a = addTag(li, "a", title);
        addAttribute(a, "href", href);
    }
}
