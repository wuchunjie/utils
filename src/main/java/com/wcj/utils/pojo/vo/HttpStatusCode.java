package com.wcj.utils.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/3/12 0012
 * @time: 下午 21:57
 * @Description: http状态码
 */
@Getter
@AllArgsConstructor
public enum  HttpStatusCode {
    //2000,成功;用户未登录,4000;用户名或密码错误,4001;账户禁用,4002;权限不足,4003;token为空,4004;用户不存在,4005;失败,5000
    /**
     * 成功
     */
    SUCCESS(2000),
    /**
     * 用户未登录
     */
    NO_LOG_IN(4000),
    /**
     * 用户名或密码错误
     */
    UNKNOWN_ACCOUNT(4001),
    /**
     * 账户禁用
     */
    DISABLE_ACCOUNT(4002),
    /**
     * 权限不足
     */
    UNAUTHENTICATED(4003),
    /**
     * token为空
     */
    NO_TOKEN(4004),
    /**
     * 用户不存在
     */
    NO_MEMBER(4005),
    /**
     * 账户锁定
     */
    LOCKED_ACCOUNT(4006),
    /**
     * 失败
     */
    FAIL(5000);

    private int code;
}
