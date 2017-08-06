package com.watent.smart.framework.helper;

import com.watent.smart.framework.annotation.Inject;
import com.watent.smart.framework.util.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手
 * <p>
 * 类
 */
public class IocHelper {

    static {
        init();
    }

    private static void init() {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isEmpty(beanMap)) {
            return;
        }
        for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
            Class<?> beanClass = beanEntry.getKey();
            Object beanInstance = beanEntry.getValue();
            Field[] beanFields = beanClass.getDeclaredFields();
            if (ArrayUtils.isEmpty(beanFields)) {
                continue;
            }
            for (Field beanField : beanFields) {
                if (!beanField.isAnnotationPresent(Inject.class)) {
                    continue;
                }
                Class<?> beanFieldClass = beanField.getType();
                Object beanFieldInstance = beanMap.get(beanFieldClass);
                if (beanFieldInstance != null) {
                    ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                }
            }
        }
    }

}
































