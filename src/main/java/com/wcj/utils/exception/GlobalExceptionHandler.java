package com.wcj.utils.exception;

import com.wcj.utils.pojo.vo.BaseResult;
import com.wcj.utils.pojo.vo.HttpStatusCode;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 *
 * @author Administrator
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 权限不足
     *
     * @param e
     */
    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public BaseResult<String> unauthorizedExceptionHandler(Exception e) {
        return BaseResult.fail(HttpStatusCode.UNAUTHENTICATED, "权限不足");
    }

    /**
     * 用户名错误
     *
     * @param e
     */
    @ExceptionHandler({UnknownAccountException.class, IncorrectCredentialsException.class})
    public BaseResult<String> unknownAccountExceptionHandler(Exception e) {
        return BaseResult.fail(HttpStatusCode.UNKNOWN_ACCOUNT, "用户名或密码错误");
    }

    /**
     * 未登录
     *
     * @param e
     */
    @ExceptionHandler({UnauthenticatedException.class, AuthenticationException.class})
    public BaseResult<String> unauthenticatedExceptionHandler(Exception e) {
        return BaseResult.fail(HttpStatusCode.NO_LOG_IN, "未登录");
    }

    /**
     * 账号禁用
     *
     * @param e
     */
    @ExceptionHandler(DisabledAccountException.class)
    public BaseResult<String> disabledAccountExceptionHandler(Exception e) {
        return BaseResult.fail(HttpStatusCode.DISABLE_ACCOUNT, "账号禁用");
    }

    /**
     * 账号锁定
     *
     * @param e
     */
    @ExceptionHandler(LockedAccountException.class)
    public BaseResult<String> lockedAccountExceptionHandler(Exception e) {
        return BaseResult.fail(HttpStatusCode.LOCKED_ACCOUNT, "账号锁定");
    }
}
