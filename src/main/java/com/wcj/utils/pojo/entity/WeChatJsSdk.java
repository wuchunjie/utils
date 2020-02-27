package com.wcj.utils.pojo.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/2/26 0026
 * @time: 下午 21:00
 * @Description:
 */
@Data
public class WeChatJsSdk {
    /**
     * 微信appid,公众号的唯一标识
     */
    private String appid;
    /**
     * 公众号的appsecret
     */
    private String secret;
    /**
     * 用户同意授权获取的code
     */
    private String code;
    /**
     * 授权类型
     */
    private String grant_type;
    /**
     * 网页授权access_token
     */
    private String access_token;
    /**
     * 用户刷新access_token
     */
    private String refresh_token;
    /**
     * 该公众号下用户唯一标识
     */
    private String openid;
    /**
     * 类型
     */
    private String type;
    /**
     * 语言
     */
    private String lang;

}
