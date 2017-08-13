package com.watent.smart.framework.aspect;

import com.watent.smart.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 拦截 Controller 所有方法
 */
public class ControllerAspect extends AspectProxy {

    private Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {

        logger.debug("--------begin-----------");
        logger.debug("class:{}", cls.getName());
        logger.debug("method:{}", method.getName());
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
        logger.debug("time:{}", System.currentTimeMillis() - begin);
        logger.debug("--------end-----------");
    }
}
