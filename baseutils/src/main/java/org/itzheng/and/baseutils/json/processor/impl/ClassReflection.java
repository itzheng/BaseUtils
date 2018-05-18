package org.itzheng.and.baseutils.json.processor.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 类反射
 * http://blog.csdn.net/linlinv3/article/details/37813873
 */
class ClassReflection {

    private static final String TAG = "ClassReflection";

    /**
     * 比较两个对象的值是否相等,前提是两个对象是同一个类型，或者有相同属性
     *
     * @param class1
     * @param class2
     * @return
     */
    public static boolean isEquals(Object class1, Object class2) {
        if (class1 == null) {
            if (class2 == null) {
                //两个对象都为空
                return true;
            } else {
                //一个空，一个不为空，肯定不相等
                return false;
            }
        } else if (class2 == null) {
            return false;
        } else if (class1.equals(class2)) {
            //两个地址或者值相等
            return true;
        }
        //需要判断是不是基本数据类型，如果两个都是基本数据类型或者一个是一个不是就返回FALSE
        if (isBaseType(class1) || isBaseType(class2)) {
            return false;
        }
        //能到这里，说明两个都不为空
        Map differentValue = getNewValues(class1, class2);
        //两个对象都不为空，返回新对象的值
        return differentValue == null || differentValue.isEmpty();
    }

    private static boolean isBaseType(Object o) {
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

    /**
     * 获取新对象的和旧对象不同的属性值
     * 1，如果新对象为空，返回必定为空，
     * 2，如果旧对象为空，返回新对象的全部值，
     * 3，如果两个对象都不为空，返回新对象和旧对象不同的值
     *
     * @param oldObj
     * @param newObj
     * @return
     */
    public static Map getNewValues(Object oldObj, Object newObj) {
        if (oldObj == null) {
            if (newObj == null) {
                return null;
            } else {
                return getObjValues(newObj);
            }
        } else if (newObj == null) {
            return null;
        }
        //开始比较新旧对象的差异值
        Map<String, Object> oldMap = getObjValues(oldObj);
        Map<String, Object> newMap = getObjValues(newObj);
        //
        if (oldMap == null || oldMap.isEmpty()) {
            return newMap;
        }
        if (newMap == null || newMap.isEmpty()) {
            return null;
        }
        Map<String, Object> newValues = new HashMap<>();
        //遍历新对象集合，然后依次比较旧对象，遍历结束，如果旧对象没有该字段的话，也将值添加差异
        for (Map.Entry<String, Object> newEntry : newMap.entrySet()) {
            String filedName = newEntry.getKey();
            Object newValue = newEntry.getValue();
            //就对象是否有该字段（属性），默认没有，当找到了就为TRUE
            boolean oldHasFiled = false;
            for (Map.Entry<String, Object> oldEntry : oldMap.entrySet()) {
                String oldFileName = oldEntry.getKey();
//                if (filedName.equals("Goods") && oldFileName.equals("Goods")) {
//                    LogHelper.d(TAG, "");
//                }
                if (filedName.equals(oldFileName)) {
                    //两个字段名相等
                    oldHasFiled = true;
                    //判断值是否相等
                    Object oldValue = oldEntry.getValue();
                    if (!isEquals(oldValue, newValue)) {
                        //两个对象的值不相等
                        newValues.put(filedName, newValue);
                    }
                }
            }
            if (!oldHasFiled) {
                newValues.put(filedName, newValue);
            }
        }
        return newValues;
    }

    /**
     * 获取一个对象的所有属性值，并且保存到map里面
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> getObjValues(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            Class clazz = Class.forName(obj.getClass().getName());
            Field[] fields = clazz.getDeclaredFields();
            ClassReflection cr = new ClassReflection();
            for (Field f : fields) {
                //获取属性
                String fieldName = f.getName();
                if (fieldName.equals("id")) {
                    continue;
                }
                if (fieldName.startsWith("this$")) {
                    continue;
                }
//                Object value = FieldUtils.getFieldValueByName(fieldName, obj);
                //打开私有访问
                f.setAccessible(true);
                //获取属性值
                Object value = f.get(obj);
                map.put(fieldName, value);
            }
        } catch (ClassNotFoundException e) {

        } catch (Exception e) {
        }
        return map;
    }

    /**
     * 把class1的值赋值给class2
     *
     * @param class1 用于赋值的实体类
     * @param class2 需要待赋值的实体类
     * @author ym
     * @CreateTime 2012-11-22下午03:23:23
     * 描述：反射实体类赋值
     */
    public static void reflectionAttr(Object class1, Object class2) throws Exception {
        Class clazz1 = Class.forName(class1.getClass().getName());
        Class clazz2 = Class.forName(class2.getClass().getName());
//      获取两个实体类的所有属性
        Field[] fields1 = clazz1.getDeclaredFields();
        Field[] fields2 = clazz2.getDeclaredFields();
        ClassReflection cr = new ClassReflection();
//      遍历class1Bean，获取逐个属性值，然后遍历class2Bean查找是否有相同的属性，如有相同则赋值
        for (Field f1 : fields1) {
            if (f1.getName().equals("id"))
                continue;
            Object value = cr.invokeGetMethod(class1, f1.getName(), null);
            for (Field f2 : fields2) {
                if (f1.getName().equals(f2.getName())) {
                    Object[] obj = new Object[1];
                    obj[0] = value;
                    cr.invokeSetMethod(class2, f2.getName(), obj);
                }
            }
        }

    }

    /**
     * 执行某个Field的getField方法
     *
     * @param clazz     类
     * @param fieldName 类的属性名称
     * @param args      参数，默认为null
     * @return
     */
    public static Object invokeGetMethod(Object clazz, String fieldName, Object[] args) {
        String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method = null;
        try {
            method = Class.forName(clazz.getClass().getName()).getDeclaredMethod("get" + methodName);
            return method.invoke(clazz);
        } catch (Exception e) {
//            e.printStackTrace();
//            LogHelper.w(TAG, "invoke get Exception");
            return "";
        }
    }

    /**
     * 执行某个Field的setField方法
     *
     * @param clazz     类
     * @param fieldName 类的属性名称
     * @param args      参数，默认为null
     * @return
     */
    public static Object invokeSetMethod(Object clazz, String fieldName, Object[] args) {
        String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method = null;
        try {
            Class[] parameterTypes = new Class[1];
            Class c = Class.forName(clazz.getClass().getName());
            Field field = c.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            method = c.getDeclaredMethod("set" + methodName, parameterTypes);
            return method.invoke(clazz, args);
        } catch (Exception e) {
//            e.printStackTrace();
//            LogHelper.w(TAG, " invoke set Exception ");
            return "";
        }
    }
}