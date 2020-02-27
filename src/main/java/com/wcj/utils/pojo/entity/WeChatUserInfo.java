package com.wcj.utils.pojo.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/2/27 0027
 * @time: 下午 14:59
 * @Description: 微信授权获取用户信息
 */
@Data
public class WeChatUserInfo {

    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<String> privilege;
    private Integer errcode;
    private String errmsg;
}
