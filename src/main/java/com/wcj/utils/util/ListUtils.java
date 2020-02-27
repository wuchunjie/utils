package com.wcj.utils.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2019/12/3 0003
 * @time: 下午 16:34
 * @Description:
 */
public class ListUtils {

    /**
     * 按照某个属性把集合进行分组
     *
     * @param list       需要分组的集合
     * @param groupField 属性名
     * @return
     */
    public static <T> Map<String, List<T>> listGroup(List<T> list, String groupField) {
        Map<String, List<T>> resultMap = new HashMap<>();
        for (T t : list) {
            List<String> childName = FieldUtils.getFieldNames(t.getClass());
            List<String> superName = FieldUtils.getFieldNames(t.getClass().getSuperclass());
            try {
                String value = null;
                if (childName.contains(groupField)) {
                    Field field = t.getClass().getDeclaredField(groupField);
                    field.setAccessible(true);
                    value = field.get(t).toString();
                }else if (superName.contains(groupField)){
                    Field field = t.getClass().getSuperclass().getDeclaredField(groupField);
                    field.setAccessible(true);
                    value = field.get(t).toString();
                }else{
                    resultMap.put(groupField,list);
                    return resultMap;
                }
                if (resultMap.containsKey(value)) {
                    resultMap.get(value).add(t);
                } else {
                    List<T> dataItem = new ArrayList<>();
                    dataItem.add(t);
                    resultMap.put(value, dataItem);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    /**
     * 把list转换成传入的对象类型
     *
     * @param list
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T, V> List<T> listTrans(List<V> list, Class<T> clazz) {
        return list.stream().map(object -> FieldUtils.fieldTrans(object, clazz)).collect(Collectors.toList());
    }
}
