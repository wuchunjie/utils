package com.wcj.utils.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/2/8 0008
 * @time: 下午 20:15
 * @Description: 中文转拼音
 */
public class PinYinUtils {

    /**
     * 将文字转为汉语拼音
     *
     * @param chineseLanguage 要转成拼音的中文全不大写,中间加字符
     */
    public static String toHanYuPinyinUpperCase(String chineseLanguage, String interval) {
        char[] clChars = chineseLanguage.trim().toCharArray();
        StringBuilder pinYin = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部大写
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        try {
            for (char clChar : clChars) {
                if (String.valueOf(clChar).matches("[\u4e00-\u9fa5]+")) {
                    // 如果字符是中文,则将中文转为汉语拼音
                    String code = PinyinHelper.toHanyuPinyinStringArray(clChar, defaultFormat)[0];
                    pinYin.append(code);
                    pinYin.append(interval);
                } else {
                    // 如果字符不是中文,则不转换
                    pinYin.append(clChar);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.getMessage();
        }
        return pinYin.toString().substring(0, pinYin.length() - 1).toUpperCase();
    }
}
