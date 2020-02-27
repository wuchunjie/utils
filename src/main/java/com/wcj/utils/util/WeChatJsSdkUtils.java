package com.wcj.utils.util;

import com.alibaba.fastjson.JSON;
import com.wcj.utils.pojo.entity.*;
import com.wcj.utils.util.httpclient.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2019/11/15 0015
 * @time: 下午 15:51
 * @Description: 微信公众号 JS-SDK工具
 */
@Slf4j
@Component
public class WeChatJsSdkUtils {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.appsecret}")
    private String appsecret;

    @Value("${wehcat.sdk.access_token}")
    private String accessTokenUrl;

    @Value("${wehcat.sdk.userinfo}")
    private String userInfoUrl;

    @Value("${wehcat.sdk.refresh_token}")
    private String refreshTokenUrl;

    @Value("${wehcat.sdk.check.access_token}")
    private String checkAccessTokenUrl;

    @Value("${wehcat.sdk.get.access_token}")
    private String getAccessTokenUrl;

    @Value("${wehcat.sdk.union}")
    private String unionUrl;

    /**
     * 通过code换取网页授权access_token
     *
     * @param code code
     * @return
     */
    public WeChatJsSdkResult getAccessToken(String code) {
        WeChatJsSdk weChatJsSdk = new WeChatJsSdk();
        weChatJsSdk.setAppid(appid);
        weChatJsSdk.setSecret(appsecret);
        weChatJsSdk.setCode(code);
        weChatJsSdk.setGrant_type("authorization_code");
        Map<String, Object> map = MapUtil.objectToMap(weChatJsSdk);
        String json = HttpClientUtil.doGet(accessTokenUrl, map);
        WeChatJsSdkResult weChatJsSdkResult = JSON.parseObject(json, WeChatJsSdkResult.class);
        if (weChatJsSdkResult == null){
            log.error(json);
            return null;
        }
        if (weChatJsSdkResult.getErrcode() != null && weChatJsSdkResult.getErrcode() != 0) {
            log.error(json);
            return null;
        }
        return weChatJsSdkResult;
    }

    /**
     * 刷新access_token（如果需要）
     *
     * @param refreshToken refresh_token
     * @return access_token
     */
    private WeChatJsSdkResult refreshToken(String refreshToken) {
        WeChatJsSdk weChatJsSdk = new WeChatJsSdk();
        weChatJsSdk.setAppid(appid);
        weChatJsSdk.setSecret(appsecret);
        weChatJsSdk.setGrant_type("refresh_token");
        weChatJsSdk.setRefresh_token(refreshToken);
        Map<String, Object> map = MapUtil.objectToMap(weChatJsSdk);
        String json = HttpClientUtil.doGet(refreshTokenUrl, map);
        WeChatJsSdkResult weChatJsSdkResult = JSON.parseObject(json, WeChatJsSdkResult.class);
        if (weChatJsSdkResult == null){
            log.error(json);
            return null;
        }
        if (weChatJsSdkResult.getErrcode() != null && weChatJsSdkResult.getErrcode() != 0) {
            log.error(json);
            return null;
        }
        return weChatJsSdkResult;
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @param openid      openid
     * @param accessToken access_token
     * @return 用户信息
     */
    public WeChatUserInfo getUserInfo(String openid, String accessToken) {
        WeChatJsSdk weChatJsSdk = new WeChatJsSdk();
        weChatJsSdk.setAccess_token(accessToken);
        weChatJsSdk.setOpenid(openid);
        weChatJsSdk.setLang("zh_CN");
        Map<String, Object> map = MapUtil.objectToMap(weChatJsSdk);
        String json = HttpClientUtil.doGet(userInfoUrl, map);
        WeChatUserInfo weChatUserInfo = JSON.parseObject(json, WeChatUserInfo.class);
        if (weChatUserInfo == null){
            log.error(json);
            return null;
        }
        if (weChatUserInfo.getErrcode() != null && weChatUserInfo.getErrcode() != 0) {
            log.error(json);
            return null;
        }
        return weChatUserInfo;
    }

    /**
     * 检验授权凭证（access_token）是否有效
     *
     * @param openid      openid
     * @param accessToken access_token
     * @return 用户信息
     */
    public boolean checkAccessToken(String openid, String accessToken) {
        WeChatJsSdk weChatJsSdk = new WeChatJsSdk();
        weChatJsSdk.setAccess_token(accessToken);
        weChatJsSdk.setOpenid(openid);
        Map<String, Object> map = MapUtil.objectToMap(weChatJsSdk);
        String json = HttpClientUtil.doGet(checkAccessTokenUrl, map);
        WeChatJsSdkResult weChatJsSdkResult = JSON.parseObject(json, WeChatJsSdkResult.class);
        return weChatJsSdkResult != null && weChatJsSdkResult.getErrcode() != null && weChatJsSdkResult.getErrcode() == 0;
    }

    /**
     * 获取微信公众号access_token
     * @return
     */
    public WeChatJsSdkResult getAccEssToken() {
        WeChatJsSdk weChatJsSdk = new WeChatJsSdk();
        weChatJsSdk.setAppid(appid);
        weChatJsSdk.setSecret(appsecret);
        weChatJsSdk.setGrant_type("client_credential");
        Map<String, Object> map = MapUtil.objectToMap(weChatJsSdk);
        String json = HttpClientUtil.doGet(getAccessTokenUrl, map);
        WeChatJsSdkResult weChatJsSdkResult = JSON.parseObject(json, WeChatJsSdkResult.class);
        if (weChatJsSdkResult == null){
            log.error(json);
            return null;
        }
        if (weChatJsSdkResult.getErrcode() != null && weChatJsSdkResult.getErrcode() != 0) {
            log.error(json);
            return null;
        }
        return weChatJsSdkResult;
    }

    /**
     * 获取jsapi_ticket
     * @param accessToken
     * @return
     */
    public WeChatJsSdkResult getJsApiTicket(String accessToken) {
        WeChatJsSdk weChatJsSdk = new WeChatJsSdk();
        weChatJsSdk.setAccess_token(accessToken);
        weChatJsSdk.setType("jsapi");
        Map<String, Object> map = MapUtil.objectToMap(weChatJsSdk);
        String json = HttpClientUtil.doGet(getAccessTokenUrl, map);
        WeChatJsSdkResult weChatJsSdkResult = JSON.parseObject(json, WeChatJsSdkResult.class);
        if (weChatJsSdkResult == null){
            log.error(json);
            return null;
        }
        if (weChatJsSdkResult.getErrcode() != null && weChatJsSdkResult.getErrcode() != 0) {
            log.error(json);
            return null;
        }
        return weChatJsSdkResult;
    }

    /**
     * 获取用户基本信息
     * @param accessToken
     * @param openId
     * @return
     */
    public WeChatUnion getUnion(String accessToken, String openId) {
        WeChatJsSdk weChatJsSdk = new WeChatJsSdk();
        weChatJsSdk.setAccess_token(accessToken);
        weChatJsSdk.setOpenid(openId);
        weChatJsSdk.setLang("zh_CN");
        Map<String, Object> map = MapUtil.objectToMap(weChatJsSdk);
        String json = HttpClientUtil.doGet(unionUrl, map);
        WeChatUnion weChatUnion = JSON.parseObject(json, WeChatUnion.class);
        if (weChatUnion == null){
            log.error(json);
            return null;
        }
        if (weChatUnion.getErrcode() != null && weChatUnion.getErrcode() != 0) {
            log.error(json);
            return null;
        }
        return weChatUnion;
    }

    /**
     * JS-SDK使用权限签名算法
     * @param url
     * @return
     */
    public WeChatJsSha1 getSignature(String url){
        WeChatJsSdkResult weChatJsSdkResult = getAccEssToken();
        if (weChatJsSdkResult == null){
            return null;
        }
        WeChatJsSdkResult jsApiTicket = getJsApiTicket(weChatJsSdkResult.getAccess_token());
        if (jsApiTicket == null){
            return null;
        }
        long millis = System.currentTimeMillis();
        WeChatJsSha1 weChatJsSha1 = new WeChatJsSha1();
        weChatJsSha1.setTimestamp(String.valueOf(millis / 1000));
        weChatJsSha1.setNoncestr(String.valueOf(millis));
        weChatJsSha1.setUrl(url);
        weChatJsSha1.setJsapi_ticket(jsApiTicket.getTicket());
        Map<String, Object> map = MapUtil.objectToMap(weChatJsSdkResult);
        String sign = MapUtil.generateSignBySha1(map);
        weChatJsSha1.setSignature(sign);
        return weChatJsSha1;
    }
}
