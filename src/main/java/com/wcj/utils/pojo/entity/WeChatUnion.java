package com.wcj.utils.pojo.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/2/27 0027
 * @time: 下午 15:50
 * @Description:
 */
@Data
public class WeChatUnion {

    private int subscribe;
    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private int subscribe_time;
    private String unionid;
    private String remark;
    private int groupid;
    private String subscribe_scene;
    private int qr_scene;
    private String qr_scene_str;
    private List<Integer> tagid_list;
    private Integer errcode;
    private String errmsg;
}
