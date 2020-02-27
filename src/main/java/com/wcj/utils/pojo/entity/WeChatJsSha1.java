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

    private String timestamp;

    private String noncestr;

    private String url;

    private String jsapi_ticket;

    private String signature;
}
