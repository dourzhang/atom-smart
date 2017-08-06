package com.watent.smart.framework.helper;

import com.watent.smart.framework.annotation.Controller;
import com.watent.smart.framework.annotation.Service;
import com.watent.smart.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手
 */
public class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getJdbcAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包下的所有类
     *
     * @return Set
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取所有 Service 类
     *
     * @return 类集合
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取所有 Controller 类
     *
     * @return 类集合
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取所有 Bean 类
     *
     * @return 类集合
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }


}
