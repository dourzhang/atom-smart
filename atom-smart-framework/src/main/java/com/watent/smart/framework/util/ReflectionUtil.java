package com.watent.smart.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 实例化
     *
     * @param cls 类
     * @return instance
     */
    public static Object newInstance(Class<?> cls) {

        Object instance;
        try {
            instance = cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("new instance failure", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     *
     * @param obj    调用者
     * @param method 方法
     * @param args   参数
     * @return 方法返回值
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {

        Object result;
        try {
            result = method.invoke(obj, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("invoke method failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置属性
     *
     * @param obj   对象
     * @param field 属性
     * @param value 值
     */
    public static void setField(Object obj, Field field, Object value) {

        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("set file failure", e);
            throw new RuntimeException(e);
        }
    }

}
