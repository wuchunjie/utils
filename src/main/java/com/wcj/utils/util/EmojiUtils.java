package com.wcj.utils.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/3/16 0016
 * @time: 下午 13:12
 * @Description:
 */
public class EmojiUtils {

    /**
     * @param str 待转换字符串
     * @return 转换后字符串
     * @Description emoji表情转换
     */
    public static String emojiConvertToUtf(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb, "[[" + URLEncoder.encode(matcher.group(1), "UTF-8") + "]]");
            } catch (UnsupportedEncodingException ignored) {

            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * @param str 转换后的字符串
     * @return 转换前的字符串
     * @Description 还原emoji表情的字符串
     */
    public static String utfemojiRecovery(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        String patternString = "\\[\\[(.*?)\\]\\]";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb, URLDecoder.decode(matcher.group(1), "UTF-8"));
            } catch (UnsupportedEncodingException ignored) {

            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


}
