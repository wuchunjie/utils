package com.wcj.utils.util;

import com.alibaba.fastjson.JSON;
import com.wcj.utils.util.httpclient.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private String wechatAppid;

    @Value("${wechat.appsecret}")
    private String appsecret;

    public Map getUnionID(String code) {
        Map map = getAccessToken(code);
        String openid = "";
        if (map != null && map.containsKey("openid")) {
            openid = map.get("openid").toString();
        }
        if (StringUtils.isNotBlank(openid)) {
            String token = getJsapiTicketAccEssToken();
            boolean subscribe = getUnionID(token, openid);
            map.put("subscribe", subscribe);
            map.put("openid", openid);
            return map;
        }
        return null;
    }

    /**
     * 通过code获取openid
     *
     * @param code
     * @return
     */
    public String getOpenId(String code) {
        Map token = getAccessToken(code);
        return token.get("openid").toString();
    }

    /**
     * 通过code获取用户信息
     *
     * @param code
     * @return
     */
    public Map getUserInfo(String code) {
        Map token = getAccessToken(code);
        if (token == null) {
            return null;
        }
        String accessToken = token.get("access_token").toString();
        String openid = token.get("openid").toString();
        Map userInfo = getUserInfo(openid, accessToken);
        if (userInfo != null) {
            userInfo.put("refresh_token", token.get("refresh_token"));
        }
        return userInfo;
    }

    /**
     * 根据用户openid和refresh_token获取用户信息
     *
     * @param openId
     * @param refreshToken
     * @return
     */
    public Map getUserInfoByOpenIdAndRefreshToken(String openId, String refreshToken) {
        //根绝refresh_token刷新access_token
        String accessToken = refreshToken(refreshToken);
        if (accessToken == null) {
            return null;
        }
        return getUserInfo(openId, accessToken);
    }

    /**
     * 通过code获取微信access_token
     *
     * @param code code
     * @return
     */
    public Map getAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wechatAppid + "&secret=" + appsecret + "&code=" + code + "&grant_type=authorization_code";
        String json = HttpClientUtil.doGet(url);
        log.info(json);
        Map map = JSON.parseObject(json, Map.class);
        if (map.containsKey("openid")) {
            return map;
        }
        return null;

    }

    /**
     * 根据access_token和openid获取用户信息
     *
     * @param openid      openid
     * @param accessToken access_token
     * @return 用户信息
     */
    public Map getUserInfo(String openid, String accessToken) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";
        String json = HttpClientUtil.doGet(url);
        log.info(json);
        Map map = JSON.parseObject(json, Map.class);
        if (map.containsKey("openid")) {
            return map;
        }
        return null;

    }

    /**
     * 通过refreshToken刷新access_token
     *
     * @param refreshToken refresh_token
     * @return access_token
     */
    private String refreshToken(String refreshToken) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + wechatAppid + "&grant_type=refresh_token&refresh_token=" + refreshToken;
        log.info("url=======" + url);
        String json = HttpClientUtil.doGet(url);
        log.info(json);
        Map map = JSON.parseObject(json, Map.class);
        if (map.containsKey("access_token")) {
            return map.get("access_token").toString();
        }
        return null;

    }

    public String getJsapiTicket() {
        String accessToken = getJsapiTicketAccEssToken();
        return getJsapiTicket(accessToken);
    }


    private String getJsapiTicketAccEssToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wechatAppid + "&secret=" + appsecret;
        log.info("url=======" + url);
        String json = HttpClientUtil.doGet(url);
        log.info(json);
        Map map = JSON.parseObject(json, Map.class);
        if (map.containsKey("access_token")) {
            return map.get("access_token").toString();
        }
        return null;
    }

    private String getJsapiTicket(String accessToken) {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
        log.info("url=======" + url);
        String json = HttpClientUtil.doGet(url);
        log.info(json);
        Map map = JSON.parseObject(json, Map.class);
        if (map.containsKey("ticket")) {
            return map.get("ticket").toString();
        }
        return null;
    }

    private boolean getUnionID(String token, String openId) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=" + openId + "&lang=zh_CN";
        log.info("url=======" + url);
        String json = HttpClientUtil.doGet(url);
        log.info(json);
        Map map = JSON.parseObject(json, Map.class);
        if (map.containsKey("subscribe")) {
            Integer subscribe = (Integer) map.get("subscribe");
            return subscribe == 1;
        }
        return false;
    }
}
