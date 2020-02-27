package com.wcj.utils.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

public class MapUtil {

    /**
     * 转换对象为map
     *
     * @param object
     * @param ignore
     * @return
     */
    public static Map<String, String> objectToMap(Object object, String... ignore) {
        Map<String, String> tempMap = new LinkedHashMap<String, String>();
        for (Field f : Objects.requireNonNull(FieldUtils.getAllFields(object.getClass()))) {
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            boolean ig = false;
            if (ignore != null && ignore.length > 0) {
                for (String i : ignore) {
                    if (i.equals(f.getName())) {
                        ig = true;
                        break;
                    }
                }
            }
            if (ig) {
                continue;
            } else {
                Object o = null;
                try {
                    o = f.get(object);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (!"serialVersionUID".equals(f.getName()) && !"FAIL".equals(f.getName()) && !"OKMSG".equals(f.getName()) && !"SUCCESS".equals(f.getName()) && !"hmac".equals(f.getName())) {
                    tempMap.put(f.getName(), o == null ? "" : o.toString());
                }
            }
        }
        return tempMap;
    }
}
