package com.wcj.utils.pojo.entity;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/2/27 0027
 * @time: 下午 16:58
 * @Description:
 */
@Data
public class WeChatPay {

    private String timeStamp;
    private String appId;
    private String packages;
    private String paySign;
    private String signType;
    private String nonceStr;

}
