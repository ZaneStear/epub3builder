package com.stear.util;

import lombok.NonNull;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public abstract class ConfigXML {
    /**
     * 向Element添加完整标签。
     *
     * @param element 待处理Element
     * @param tag     标签名
     * @param text    标签内容
     * @param attrs   属性名与属性值
     * @return element
     */
    protected Element addTag(Element element, @NonNull String tag, String text, Map<String, String> attrs) {
        Element resultElement = element.addElement(tag);
        if (text != null) resultElement.addText(text);
        if (attrs != null) {
            for (Map.Entry<String, String> attr :
                    attrs.entrySet()) {
                resultElement.addAttribute(attr.getKey(), attr.getValue());
            }
        }
        return resultElement;
    }

    /**
     * 向Element添加带Text的标签。
     *
     * @param element 待处理Element
     * @param tag     标签名
     * @param text    Text
     * @return element
     */
    protected Element addTag(Element element, String tag, String text) {
        return addTag(element, tag, text, null);
    }

    /**
     * 向Element添加空标签
     *
     * @param element 待处理Element
     * @param tag     标签名
     * @return element
     */
    protected Element addTag(Element element, String tag) {
        return addTag(element, tag, null, null);
    }

    public Element addAttribute(Element element, String name, String value) {
        if (value != null) {
            return element.addAttribute(name, value);
        }
        return element;
    }

    /**
     * 向manifest标签加入最基本item标签
     *
     * @param manifest  manifest
     * @param id        id
     * @param href      相对于package.opf的路径，如html/chapter1.html
     * @param mediaType 资源类型，详情见{@link com.stear.service.MediaTypeService}
     */
    protected Element addItemToManifest(Element manifest, String id, String href, String mediaType) {
        Map<String, String> attrs = new HashMap<>();
        attrs.put("id", id);
        attrs.put("href", href);
        attrs.put("media-type", mediaType);
        return addTag(manifest, "item", null, attrs);
    }
}
