package com.watent.smart.framework.helper;


import com.watent.smart.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean 助手类
 * <p>
 * Bean容器 Bean类与Bean实例关系
 */
public class BeanHelper {

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beaClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beaClass);
            BEAN_MAP.put(beaClass, obj);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) throws RuntimeException {

        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("Can't get bean by class:{}" + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }


}
