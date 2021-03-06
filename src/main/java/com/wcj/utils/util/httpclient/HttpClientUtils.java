package com.wcj.utils.util.httpclient;

import com.wcj.utils.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2019/11/23 0023
 * @time: 下午 21:03
 * @Description: http工具
 */
@Slf4j
public class HttpClientUtils {

    /**
     * get请求获取json
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * get请求获取json
     *
     * @param url
     * @return
     */
    public static String doGet(String url, Map<String, String> paramMap) {
        try {
            CloseableHttpClient client = HttpClientPool.getConnection();
            String builderUrl = buildUrlQuery(url, paramMap);
            log.info("httpClient.doGet.Url=" + builderUrl);
            HttpGet request = new HttpGet(builderUrl);
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity, "utf-8");
            log.info("httpClient.doGet.Result=" + json);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Post请求获取json
     *
     * @param url 请求地址
     * @return
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * Post请求获取json
     *
     * @param url      请求地址
     * @param paramMap url参数
     * @return
     */
    public static String doPost(String url, Map<String, String> paramMap) {
        return doPost(url, null, paramMap);
    }

    /**
     * Post请求获取json
     *
     * @param url      请求地址
     * @param headers  请求头
     * @param paramMap url参数
     * @return
     */
    public static String doPost(String url, Map<String, String> headers, Map<String, String> paramMap) {
        return doPost(url, headers, paramMap, null);
    }

    /**
     * Post 提交json 请求获取json
     *
     * @param url      请求地址
     * @param headers  请求头
     * @param paramMap url参数
     * @param body     json参数
     * @return
     */
    public static String doPost(String url, Map<String, String> headers, Map<String, String> paramMap, String body) {
        try {
            CloseableHttpClient client = HttpClientPool.getConnection();
            String builderUrl = buildUrlQuery(url, paramMap);
            log.info("httpClient.doPost.Url=" + builderUrl);
            HttpPost request = new HttpPost(builderUrl);
            if (StringUtils.isNotBlank(body)) {
                request.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
            }
            if (headers != null) {
                headers.remove(HttpHeaders.CONTENT_TYPE);
                headers.forEach(request::addHeader);
            }
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity, "utf-8");
            log.info("httpClient.doPost.Result=" + json);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Post 提交表单 请求获取json
     *
     * @param url      请求地址
     * @param headers  请求头
     * @param paramMap url参数
     * @param body     表单参数
     * @return
     */
    public static String doPostByForm(String url, Map<String, String> headers, Map<String, String> paramMap, Map<String, String> body) {
        try {
            CloseableHttpClient client = HttpClientPool.getConnection();
            String builderUrl = buildUrlQuery(url, paramMap);
            log.info("httpClient.doPost.Url=" + builderUrl);
            HttpPost request = new HttpPost(builderUrl);
            if (body != null) {
                List<NameValuePair> nameValuePairList = new ArrayList<>();
                body.forEach((key, value) -> {
                    nameValuePairList.add(new BasicNameValuePair(key, value));
                });
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
                formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
                request.setEntity(formEntity);
            }
            if (headers != null) {
                headers.remove(HttpHeaders.CONTENT_TYPE);
                headers.forEach(request::addHeader);
            }
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取post方法的参数
     *
     * @param request
     * @return String
     */
    public static String getPostParam(HttpServletRequest request) {
        InputStream is = null;
        String contentStr = "";
        try {
            is = request.getInputStream();
            contentStr = IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return contentStr;
    }

    /**
     * 将传入的参数，发送到前端，解决编码问题
     *
     * @param response
     * @param json
     */
    public static void sendResponse(HttpServletResponse response, String json) {
        //设置网页编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            //此处不考虑将异常抛出
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    private static String buildUrlQuery(String url, Map<String, String> querys) {
        if (querys == null) {
            return url;
        }
        String last = url.substring(url.length() - 1);
        if ("/".equals(last)) {
            url = url.substring(0, url.length() - 1);
        }
        return url + buildUrlQuery(querys);
    }

    private static String buildUrlQuery(Map<String, String> querys) {
        return "?" + MapUtils.mapJoin(querys, false, true);
    }
}
