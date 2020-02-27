package com.wcj.utils.pojo.entity;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/2/27 0027
 * @time: 下午 16:26
 * @Description: JS-SDK使用权限签名算法
 */
@Data
public class WeChatJsSha1 {
    /**
     * 时间戳(秒)
     */
    private String timestamp;
    /**
     * 随机字符串
     */
    private String noncestr;
    /**
     * 授权url
     */
    private String url;
    /**
     * jsapi_ticket
     */
    private String jsapi_ticket;
    /**
     * 签名
     */
    private String signature;
}
