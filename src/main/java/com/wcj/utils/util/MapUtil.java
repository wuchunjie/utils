package com.wcj.utils.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Map.Entry;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2019/12/23 0023
 * @time: 下午 15:28
 * @Description: map的一些工具
 */
public class MapUtil {
    /**
     * Description: 替换map里的null为""
     * @param map
     * @return
     */
    public static Map<String, Object> mapRemoveWithNullByRecursion(Map<String, Object> map) {
        Set<Entry<String, Object>> set = map.entrySet();
        Iterator<Entry<String, Object>> it = set.iterator();
        Map map2;
        while (it.hasNext()) {
            Entry<String, Object> en = it.next();
            if (!(en.getValue() instanceof Map)) {
                if (null == en.getValue() || "null".equals(en.getValue())) {
                    en.setValue("");
                }
            } else {
                map2 = (Map) en.getValue();
                mapRemoveWithNullByRecursion(map2);
            }
        }
        return map;
    }

    /**
     * 微信支付签名,MD5加密
     *
     * @param map
     * @param key
     * @return
     */
    public static String generateSignByMd5(Map<String, Object> map, String key) {
        Map<String, Object> tempMap = order(map);
        tempMap.remove("sign");
        String str = mapJoin(tempMap, false, false);
        return DigestUtils.md5Hex(str + "&key=" + key).toUpperCase();
    }

    /**
     * 微信权限签名,sha1加密
     *
     * @param map
     * @return
     */
    public static String generateSignBySha1(Map<String, Object> map) {
        Map<String, Object> tempMap = order(map);
        tempMap.remove("sign");
        String str = mapJoin(tempMap, false, false);
        return DigestUtils.sha1Hex(str);
    }

    /**
     * Map key 正序排序
     *
     * @param map
     * @return
     */
    public static Map<String, Object> order(Map<String, Object> map) {
        HashMap<String, Object> tempMap = new LinkedHashMap<>();
        List<Map.Entry<String, Object>> infoIds = new ArrayList<>(map.entrySet());
        infoIds.sort(Map.Entry.comparingByKey());
        infoIds.forEach(item -> tempMap.put(item.getKey(), item.getValue()));
        return tempMap;
    }

    /**
     * url 参数串连
     *
     * @param map             参数
     * @param keyLower        是否小写
     * @param valueUrlEncoder 是否url encoder
     * @return key=value&key=value&key=value
     */
    public static String mapJoin(Map<String, Object> map, boolean keyLower, boolean valueUrlEncoder) {
        StringBuilder builder = new StringBuilder();
        map.forEach((key, value) -> {
            if (StringUtils.isNotBlank(key)) {
                try {
                    String temp = (key.endsWith("_") && key.length() > 1) ? key.substring(0, key.length() - 1) : key;
                    builder.append(keyLower ? temp.toLowerCase() : temp)
                            .append("=")
                            .append(valueUrlEncoder ? URLEncoder.encode(value.toString(), "utf-8").replace("+", "%20") : value)
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }


    /**
     * 表单提交数据格式（即key=value&key=value...）转成Map
     *
     * @param formParam
     * @return
     */
    public static Map<String, Object> formParamToMap(String formParam) {
        Map<String, Object> map = new HashMap<>();
        String[] keysAndValues = formParam.split("&");
        for (String keyAndValue : keysAndValues) {
            map.put(keyAndValue.split("=")[0], keyAndValue.split("=")[1]);
        }
        return map;
    }

    /**
     * 简单 map 转换为 实体类
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        List<Field> fields = FieldUtils.getAllFields(obj.getClass());
        if (fields == null) {
            return map;
        }
        fields.forEach(field -> {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return map;
    }

    /**
     * 简单 xml 转换为 实体类
     *
     * @param xml
     * @param clazz
     * @return
     */
    public static <T> T xmlToObject(String xml, Class<T> clazz) {
        Map<String, Object> map = xmlToMap(xml);
        return mapToObject(map, clazz);
    }

    /**
     * 简单 map 转换为 实体类
     *
     * @param map
     * @return
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        try {
            T t = clazz.newInstance();
            map.forEach((key, value) -> {
                FieldUtils.setFieldValue(key, value, t);
            });
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 简单 xml 转换为 Map
     *
     * @param xml
     * @return
     */
    public static Map<String, Object> xmlToMap(String xml) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getChildNodes();
            Map<String, Object> map = new LinkedHashMap<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);
                map.put(e.getNodeName(), e.getTextContent());
            }
            return map;
        } catch (DOMException | ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
}
