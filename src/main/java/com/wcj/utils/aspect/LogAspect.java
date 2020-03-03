package com.wcj.utils.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;


/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2019/7/10 0010
 * @time: 下午 13:34
 * @Description: 日志管理
 */
@Aspect
@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);
    private static long startTime;
    private static long endTime;

    //TODO 此处填写项目实际的controller的路径(切面位置)
    @Pointcut("execution(public * com.wcj.utils.controller.*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
        //获取上下文request容器
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null){
            return;
        }
        //获取request
        HttpServletRequest request = attributes.getRequest();
        logger.info("========================================== Start ==========================================");
        //请求地址
        logger.info("   URL:              {}", request.getRequestURL().toString());
        //请求方法
        logger.info("   HttpMethod:       {}", request.getMethod());
        //header中获取token
        logger.info("   token:            {}", request.getHeader("token"));
        //获取请求的入参
        Object[] args = joinPoint.getArgs();
        try {
            //如果入参有文件的话不转成json
            if (args == null || args.length == 0 || args[0] instanceof MultipartFile) {
                logger.info("   Param:            {}", args);
            } else {
                logger.info("   Param:            {}", JSON.toJSONStringWithDateFormat(args, "yyyy-MM-dd HH:mm:ss"));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @AfterReturning(value = "webLog()", returning = "object")
    public void doAfter(Object object) {
        endTime = System.currentTimeMillis() - startTime;
        try {
            logger.info("   Response:         {}", JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("   Time:             {}mm", endTime);
        logger.info("=========================================== End ===========================================");
    }

}

