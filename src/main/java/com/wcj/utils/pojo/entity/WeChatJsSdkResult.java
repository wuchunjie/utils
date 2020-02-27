package com.wcj.utils.pojo.entity;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/2/27 0027
 * @time: 下午 14:34
 * @Description:
 */
@Data
public class WeChatJsSdkResult {
    /**
     * 网页授权接口调用凭证
     */
    private String access_token;
    /**
     * access_token接口调用凭证超时时间，单位（秒）
     */
    private Integer expires_in;
    /**
     * 用户刷新access_token
     */
    private String refresh_token;
    /**
     * 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
     */
    private String openid;
    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    private String scope;
    /**
     * jsapi_ticket
     */
    private String ticket;
    /**
     * 错误代码
     */
    private Integer errcode;
    /**
     * 错误信息
     */
    private String errmsg;
}
