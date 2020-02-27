package com.wcj.utils.interceptor;

import com.wcj.utils.annotation.DisableAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2019/7/15 0015
 * @time: 下午 14:44g
 * @Description: token拦截
 */
@Slf4j
@Component
public class TokenHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            //获取方法上是否存在自定义注解
            DisableAuth disableAuth = method.getMethodAnnotation(DisableAuth.class);
            //存在自定义放行注解,放行接口
            if (disableAuth != null){
                return true;
            }
        }
        String token = request.getHeader("token");
        if (token == null) {
            response.sendError(4003, "token不存在");
            return false;
        } else {
            //TODO 按照toke查询用户,判断用户是否存在
            response.sendError(4003, "用户不存在");
        }
        return true;
    }
}
