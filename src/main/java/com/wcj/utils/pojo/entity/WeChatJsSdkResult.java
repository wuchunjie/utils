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

    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String ticket;
    private Integer errcode;
    private String errmsg;
}
