package com.wcj.utils.util.httpclient;

import com.wcj.utils.util.XMLConverUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class XmlResponseHandler {

    private static Map<String, ResponseHandler<?>> map = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> ResponseHandler<T> createResponseHandler(final Class<T> clazz) {
        if (map.containsKey(clazz.getName())) {
            return (ResponseHandler<T>) map.get(clazz.getName());
        } else {
            ResponseHandler<T> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    String str = EntityUtils.toString(entity);
                    return XMLConverUtil.convertToObject(clazz, new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }

            };
            map.put(clazz.getName(), responseHandler);
            return responseHandler;
        }
    }

}
