package com.wcj.utils.pojo.entity;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/3/19 0019
 * @time: 上午 10:24
 * @Description: 微信模板消息
 */
@Data
public class WeChatTemplateMessage {
    /**
     * 接收人openid
     */
    private String touser;
    /**
     * 模板id
     */
    private String template_id;
    /**
     * 跳转链接
     */
    private String url;
    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据
     */
    private WeChatMiniprogram miniprogram;
    private WeChatTemplateData data;
}
