package com.wcj.utils.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2019/12/23 0023
 * @time: 下午 15:28
 * @Description: 反射的一些工具
 */
public class FieldUtils {

    /**
     * 验证对象的某些属性是否为空
     *
     * @param obj
     * @param names
     * @return true 有为空,false 没有为空
     */
    public static boolean isFieldEmpty(Object obj, String... names) {
        for (String name : names) {
            boolean empty = isFieldEmpty(obj, name);
            if (empty) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证对象的某个属性是否为空
     *
     * @param obj
     * @param names
     * @return true 不为空,false 有为空
     */
    public static boolean isFieldNotEmpty(Object obj, String... names) {
        return !isFieldEmpty(obj, names);
    }

    /**
     * 验证对象的某个属性是否为空
     *
     * @param obj
     * @param name
     * @return true 有为空,false 没有为空
     */
    public static boolean isFieldEmpty(Object obj, String name) {
        if (obj == null) {
            return true;
        }
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value == null) {
                return true;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 验证对象的某个属性是否为空
     *
     * @param obj
     * @param name
     * @return true 不为空,false 有为空
     */
    public static boolean isFieldNotEmpty(Object obj, String name) {
        return !isFieldEmpty(obj, name);
    }

    /**
     * 验证对象的属性是否有为空
     *
     * @param obj
     * @return
     */
    public static boolean hasEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value == null) {
                    return true;
                } else {
                    if (StringUtils.isBlank(value.toString())) {
                        return true;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 验证对象的属性是否全部不为空
     *
     * @param obj
     * @return
     */
    public static boolean hasNotEmpty(Object obj) {
        if (obj == null) {
            return false;
        }
        return !hasEmpty(obj);
    }

    /**
     * 把一个对象转成另一个指定class的对象
     *
     * @param t     源对象
     * @param clazz 需要转成的class
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> V fieldTrans(T t, Class<V> clazz) {
        List<Field> allFields = getAllFields(t.getClass());
        try {
            V v = clazz.newInstance();
            if (allFields != null) {
                allFields.forEach(field -> setFieldValue(t, field, v));
            }
            return v;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把一个对象的值覆盖另一个对象的值
     *
     * @param t   源对象
     * @param v   赋值的对象
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> V fieldTrans(T t, V v) {
        List<Field> allFields = getAllFields(t.getClass());
        if (allFields != null) {
            allFields.forEach(field -> setFieldValue(t, field, v));
        }
        return v;
    }

    /**
     * 根据一个对象的字段的值赋值给另一个对象
     *
     * @param t     源对象
     * @param field 字段
     * @param v     被赋值的对象
     * @param <T>
     * @param <V>
     */
    public static <T, V> void setFieldValue(T t, Field field, V v) {
        if (field == null) {
            return;
        }
        field.setAccessible(true);
        String name = field.getName();
        try {
            Field declaredField = getFieldByName(v.getClass(), name);
            if (declaredField == null) {
                return;
            }
            //字段如果是final修饰的则跳过
            if (Modifier.isFinal(declaredField.getModifiers())) {
                return;
            }
            //字段类型不同,则不赋值
            if (!field.getGenericType().getTypeName().equals(declaredField.getGenericType().getTypeName())) {
                return;
            }
            declaredField.setAccessible(true);
            declaredField.set(v, field.get(t));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据字段名给另一个对象赋值
     *
     * @param name  字段名
     * @param value 值
     * @param t     被赋值的对象
     * @param <T>
     */
    public static <T> void setFieldValue(String name, Object value, T t) {
        try {
            Field field = getFieldByName(t.getClass(), name);
            if (field == null) {
                return;
            }
            //字段如果是final修饰的则跳过
            if (Modifier.isFinal(field.getModifiers())) {
                return;
            }
            field.setAccessible(true);
            field.set(t, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根绝字段名获取对象的字段
     *
     * @param clazz
     * @param name
     * @return
     */
    public static Field getFieldByName(Class<?> clazz, String name) {
        if (!clazz.equals(Object.class)) {
            List<String> fieldNames = getFieldNames(clazz);
            if (fieldNames.contains(name)) {
                try {
                    return clazz.getDeclaredField(name);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            } else {
                return getFieldByName(clazz.getSuperclass(), name);
            }
            return null;
        }
        return null;
    }

    /**
     * 获取该对象的属性名集合
     *
     * @param clazz
     * @return
     */
    public static List<String> getFieldNames(Class<?> clazz) {
        List<String> names = new ArrayList<>();
        List<Field> fields = getFields(clazz);
        if (fields != null) {
            fields.forEach(field -> names.add(field.getName()));
        }
        return names;
    }

    /**
     * 获取该对象的属性名集合
     *
     * @param clazz
     * @return
     */
    public static List<Field> getFields(Class<?> clazz) {
        if (!clazz.equals(Object.class)) {
            return new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        }
        return null;
    }

    /**
     * 获取所有Field的name,包含父类field
     *
     * @param clazz
     * @return
     */
    public static List<String> getAllFieldNames(Class<?> clazz) {
        List<String> names = new ArrayList<>();
        List<Field> allFields = getAllFields(clazz);
        if (allFields != null) {
            allFields.forEach(field -> names.add(field.getName()));
        }
        return names;
    }

    /**
     * 获取所有Fields,包含父类field
     *
     * @param clazz
     * @return
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        if (!clazz.equals(Object.class)) {
            List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
            List<Field> fields2 = getAllFields(clazz.getSuperclass());
            if (fields2 != null) {
                fields.addAll(fields2);
            }
            return fields;
        }
        return null;
    }
}
