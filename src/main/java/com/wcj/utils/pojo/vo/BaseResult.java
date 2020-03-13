package com.wcj.utils.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResult<T> {

    @ApiModelProperty("状态码(2000,成功;用户未登录,4000;用户名或密码错误,4001;账户禁用,4002;权限不足" +
            ",4003;token为空,4004;用户不存在,4005;账户锁定,4006;失败,5000)")
    private Integer resultCode;

    @ApiModelProperty("响应信息")
    private String resultMsg;

    @ApiModelProperty("返回值")
    private T data;

    public static <T> BaseResult<T> success() {
        return success(HttpStatusCode.SUCCESS.getCode(), HttpStatusCode.SUCCESS.toString(), null);
    }

    public static <T> BaseResult<T> successCode(HttpStatusCode code) {
        return success(code.getCode(), HttpStatusCode.SUCCESS.toString(), null);
    }

    public static <T> BaseResult<T> successMsg(String resultMsg) {
        return success(HttpStatusCode.SUCCESS.getCode(), resultMsg, null);
    }

    public static <T> BaseResult<T> successData(T t) {
        return success(HttpStatusCode.SUCCESS.getCode(), HttpStatusCode.SUCCESS.toString(), t);
    }

    public static <T> BaseResult<T> successMsgAndData(T t) {
        return success(HttpStatusCode.SUCCESS.getCode(), HttpStatusCode.SUCCESS.toString(), t);
    }

    public static <T> BaseResult<T> success(HttpStatusCode code, String resultMsg) {
        return getBaseResult(code.getCode(), resultMsg, null);
    }

    public static <T> BaseResult<T> success(Integer resultCode, String resultMsg, T t) {
        return getBaseResult(resultCode, resultMsg, t);
    }

    public static <T> BaseResult<T> fail() {
        return fail(HttpStatusCode.FAIL.getCode(), HttpStatusCode.FAIL.toString(), null);
    }

    public static <T> BaseResult<T> failCode(HttpStatusCode code) {
        return fail(code.getCode(), HttpStatusCode.FAIL.toString(), null);
    }

    public static <T> BaseResult<T> failMsg(String resultMsg) {
        return fail(HttpStatusCode.FAIL.getCode(), resultMsg, null);
    }

    public static <T> BaseResult<T> failData(T t) {
        return fail(HttpStatusCode.FAIL.getCode(), HttpStatusCode.FAIL.toString(), t);
    }

    public static <T> BaseResult<T> failMsgAndData(T t) {
        return fail(HttpStatusCode.FAIL.getCode(), HttpStatusCode.FAIL.toString(), t);
    }

    public static <T> BaseResult<T> fail(HttpStatusCode code, String resultMsg) {
        return getBaseResult(code.getCode(), resultMsg, null);
    }

    public static <T> BaseResult<T> fail(Integer resultCode, String resultMsg, T t) {
        return getBaseResult(resultCode, resultMsg, t);
    }

    public static <T> BaseResult<T> getBaseResult(Integer resultCode, String resultMsg, T t) {
        return new BaseResult<>(resultCode, resultMsg, t);
    }
}
