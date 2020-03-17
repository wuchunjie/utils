package com.wcj.utils.pojo.entity;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/3/17 0017
 * @time: 下午 13:10
 * @Description: 微信消息
 */
@Data
public class WeChatMessage {

    private String touser;
    private String msgtype;
    private WeChatText text;

}
