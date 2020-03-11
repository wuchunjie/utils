package com.wcj.utils.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * XML 数据接收对象转换工具类
 */
public class XMLConverUtil {

    private static final ThreadLocal<Map<Class<?>, Marshaller>> mMapLocal = ThreadLocal.withInitial(HashMap::new);

    private static final ThreadLocal<Map<Class<?>, Unmarshaller>> uMapLocal = ThreadLocal.withInitial(HashMap::new);

    /**
     * XML to Object
     *
     * @param <T>
     * @param clazz
     * @param xml
     * @return
     */
    public static <T> T convertToObject(Class<T> clazz, String xml) {
        return convertToObject(clazz, new StringReader(xml));
    }

    /**
     * XML to Object
     *
     * @param <T>
     * @param clazz
     * @param inputStream
     * @return
     */
    public static <T> T convertToObject(Class<T> clazz, InputStream inputStream) {
        return convertToObject(clazz, new InputStreamReader(inputStream));
    }

    /**
     * XML to Object
     *
     * @param <T>
     * @param clazz
     * @param reader
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToObject(Class<T> clazz, Reader reader) {
        try {
            Map<Class<?>, Unmarshaller> uMap = uMapLocal.get();
            if (!uMap.containsKey(clazz)) {
                JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                uMap.put(clazz, unmarshaller);
            }
            return (T) uMap.get(clazz).unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object to XML
     *
     * @param object
     * @return
     */
    public static String convertToXML(Object object) {
        try {
            Map<Class<?>, Marshaller> mMap = mMapLocal.get();
            StringWriter stringWriter = new StringWriter();
            if (!mMap.containsKey(object.getClass())) {
                JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                XMLStreamWriter streamWriter = XMLOutputFactory.newInstance()
                        .createXMLStreamWriter(stringWriter);
                // *) 使用动态代理模式, 对streamWriter功能进行干涉调整
                XMLStreamWriter cdataStreamWriter = (XMLStreamWriter) Proxy.newProxyInstance(
                        streamWriter.getClass().getClassLoader(),
                        streamWriter.getClass().getInterfaces(),
                        new CDataHandler(streamWriter)
                );
                marshaller.marshal(object, cdataStreamWriter);
                mMap.put(object.getClass(), marshaller);
            }
            mMap.get(object.getClass()).marshal(object, stringWriter);
            return stringWriter.getBuffer().toString();
        } catch (JAXBException | XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class CDataHandler implements InvocationHandler {
        // *) 单独拦截 writeCharacters(String)方法
        private static Method gWriteCharactersMethod = null;

        static {
            try {
                gWriteCharactersMethod = XMLStreamWriter.class
                        .getDeclaredMethod("writeCharacters", String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        private XMLStreamWriter writer;

        public CDataHandler(XMLStreamWriter writer) {
            this.writer = writer;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (gWriteCharactersMethod.equals(method)) {
                String text = (String) args[0];
                // *) 遇到CDATA标记时, 则转而调用writeCData方法
                if (text != null && text.startsWith("<![CDATA[") && text.endsWith("]]>")) {
                    writer.writeCData(text.substring(9, text.length() - 3));
                    return null;
                }
            }
            return method.invoke(writer, args);
        }

    }
}
