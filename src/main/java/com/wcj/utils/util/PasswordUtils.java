package com.wcj.utils.util;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/3/22 0022
 * @time: 上午 11:24
 * @Description: 密码加密
 */
public class PasswordUtils {

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * 用户输入的密码,前后一半调换位置,md5加密之后,再次前后交换,最后md5
     *
     * @param password
     * @return
     */
    public static String getPassword(String password) {
        password = password.trim();
        StringBuffer buffer = new StringBuffer();
        int i = password.length() / 2;
        buffer.append(password, i, password.length());
        buffer.append(password, 0, i);
        password = DigestUtils.md5Hex(buffer.toString());
        buffer = new StringBuffer();
        i = password.length() / 2;
        buffer.append(password, i, password.length());
        buffer.append(password, 0, i);
        return DigestUtils.md5Hex(buffer.toString());
    }

    /**
     * 密码盐值加密
     *
     * @param password
     * @param salt
     * @return
     */
    public static String getPassword(String password, String salt) {
        if (StringUtils.isNotBlank(salt)) {
            password = password + salt;
        }
        return getPassword(password);
    }

    /**
     * 获取随机6位数
     *
     * @return
     */
    public static String getRandom() {
        return (int) ((Math.random() * 9 + 1) * 100000) + "";
    }

    /**
     * 获取随机6位数
     *
     * @return
     */
    public static String getRandom2() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
