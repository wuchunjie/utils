package com.wcj.utils.util;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/3/20 0020
 * @time: 下午 14:16
 * @Description:
 */
public class MybatisPlusUtils {
    /**
     * 实体类非空生成查询条件
     *
     * @param t
     * @param stringLike String类型是否使用like
     * @param skipDate   是否跳过时间类型
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> QueryWrapper<T> getQueryWrapper(T t, boolean stringLike, boolean skipDate) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (t == null) {
            return queryWrapper;
        }
        List<Field> fields = FieldUtils.getFields(t.getClass());
        for (Field field : fields) {
            Class<?> type = field.getType();
            if (skipDate) {
                if (type.equals(Date.class) || type.equals(LocalDate.class)) {
                    continue;
                }
            }
            field.setAccessible(true);
            //序列化 字段不需要查询
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            //属性为空，不用查询
            Object value = field.get(t);
            String name = null;
            if (value == null || StringUtils.isBlank(value.toString())) {
                continue;
            }
            //主键 注解TableId
            TableId tableId = field.getAnnotation(TableId.class);
            //数据库中字段名和实体类属性不一致 注解TableField
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableId != null) {
                //主键
                name = tableId.value();
            } else if (tableField != null) {
                //数据库中字段名和实体类属性不一致 注解TableField
                if (tableField.exist()) {
                    name = tableField.value();
                }
            } else {
                name = StringLineHump.humpToLine(field.getName());
            }
            if (StringUtils.isBlank(name)) {
                continue;
            }
            if (stringLike && type.equals(String.class)) {
                queryWrapper.like(name, value);
            } else {
                queryWrapper.eq(name, value);
            }
        }
        return queryWrapper;
    }
}
