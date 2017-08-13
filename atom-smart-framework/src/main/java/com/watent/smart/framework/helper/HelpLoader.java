package com.watent.smart.framework.helper;

import com.watent.smart.framework.util.ClassUtil;

/**
 * 加载相应的 Helper 类
 * <p>
 * AopHelper 在 IocHelper 之前
 * 先获取代理对象然后依赖注入
 */
public class HelpLoader {

    public static void init() {

        Class<?>[] classList = {ClassHelper.class, BeanHelper.class, AopHelper.class, IocHelper.class, ConfigHelper.class};
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }

}
