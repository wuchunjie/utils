package com.wcj.utils.annotation;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/1/19 0019
 * @time: 下午 21:10
 * @Description: 自定义注解,作用在controller方法上,放行接口
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface DisableAuth {
}
