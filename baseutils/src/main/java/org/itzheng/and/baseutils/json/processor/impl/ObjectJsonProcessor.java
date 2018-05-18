package org.itzheng.and.baseutils.json.processor.impl;


import android.util.Log;

import org.itzheng.and.baseutils.json.processor.IJsonProcessor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by daniel on 17-4-27.
 */

public class ObjectJsonProcessor implements IJsonProcessor {
    private static final String TAG = "ObjectJsonProcessor";

    @Override
    public String toJson(Object obj) {
        return objToJson(obj);
    }

    /**
     * 自己手写的将对象转json
     *
     * @param obj
     * @return
     */
    private String objToJson(Object obj) {
        if (obj == null) {
            return "";
        }
        if (isPrimitive(obj)) {
            return obj.toString();
        }
//        JSONObject jsonObject = new JSONObject();
        //通过反射的方式，获取对象的属性名和属性值
        Map<String, Object> values = ClassReflection.getObjValues(obj);
        //遍历values
        StringBuffer json = new StringBuffer();
        json.append("{");
        for (Map.Entry<String, Object> entries : values.entrySet()) {
            Object value = entries.getValue();
            if (value == null) {
                continue;
            }
            json.append("\"" + entries.getKey() + "\"");
            json.append(":");
            if (isPrimitive(value)) {
                if (value instanceof String) {
                    json.append("\"" + value + "\"");
                } else {
                    json.append(value);
                }
            } else {
                json.append(objToJson(value));
            }
            json.append(",");
        }
        if (json.length() > 1) {
            //减去最后一个逗号
            json.deleteCharAt(json.length() - 1);
        }
        json.append("}");
        return json.toString();
    }

    /**
     * 是否基本数据类型
     *
     * @param o
     * @return
     */
    private boolean isPrimitive(Object o) {
        if (o instanceof String ||
                o instanceof Integer ||
                o instanceof Long ||
                o instanceof Double ||
                o instanceof Float ||
                o instanceof Byte
                ) {
            return true;
        }
        return false;
    }

    @Override
    public <T> T fromJson(String str, Type type) {
        return null;
    }

    @Override
    public <T> T fromJson(String str, Class<T> type) {
        T instance = null;
        try {
            instance = type.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (instance == null) {
            return null;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject == null) {
            return instance;
        }
        //通过反射的方式，获取对象的属性名和属性值
//        String[] filedNames = FieldUtils.getFiledName(instance);
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj = null;
            try {
                obj = jsonObject.get(field.getName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (obj == null) {
                continue;
            }
            if (isPrimitive(obj)) {
                FieldUtils.setFieldValue(instance, field.getName(), obj);
            } else {
                Object subObj = fromJson(obj.toString(), field.getDeclaringClass());
                FieldUtils.setFieldValue(instance, field.getName(), subObj);
            }
        }
        return instance;
    }

    @Override
    public <T> String mapToJson(Map<String, T> map) {
        return null;
    }

    @Override
    public <T> List<T> jsonToList(String str, Class<T> type) {
        return null;
    }
}
