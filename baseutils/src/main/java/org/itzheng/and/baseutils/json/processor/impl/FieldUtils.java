package org.itzheng.and.baseutils.json.processor.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WL001 on 2017/5/17.
 */

class FieldUtils {
    /**
     * 根据属性名获取属性值
     */
    public static Object getFieldValueByName(String fieldName, Object o) {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        Method method = null;
        Object value = null;
        try {
            Class<? extends Object> clazz = o.getClass();
            method = clazz.getMethod(getter, new Class[]{});
            value = method.invoke(o, new Object[]{});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取属性名数组
     */
    public static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 给对象的属性赋值
     *
     * @param instance 对象
     * @param field    属性名
     * @param obj      属性值
     * @param <T>
     */
    public static <T> void setFieldValue(T instance, String field, Object obj) {
        Field f = null;
        try {
            //根据对象获取类型，获取对应的属性（字段）
            f = instance.getClass().getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        //设置字段可访问
        f.setAccessible(true);
        try {
            //给对象的字段赋值
            f.set(instance, obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Field[] getDeclaredFields(Object o) {
        return o.getClass().getDeclaredFields();
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     */
    public static List getFiledsInfo(Object o) throws Exception {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        List list = new ArrayList();
        Map infoMap = null;
        for (int i = 0; i < fields.length; i++) {
            infoMap = new HashMap();
            infoMap.put("type", fields[i].getType().toString());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }

    /**
     * 获取对象的所有属性值，返回一个对象数组
     */
    public static Object[] getFiledValues(Object o) throws Exception {
        String[] fieldNames = getFiledName(o);
        Object[] value = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            value[i] = getFieldValueByName(fieldNames[i], o);
        }
        return value;
    }
}
