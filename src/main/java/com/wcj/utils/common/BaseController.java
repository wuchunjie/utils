package com.wcj.utils.common;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2019/12/9 0009
 * @time: 上午 11:25
 * @Description:
 */
public class BaseController {

    protected String token;

    @ModelAttribute
    public void setReqAndResp(HttpServletRequest request, HttpServletResponse response) {
        this.token = request.getHeader("token");
    }
}
