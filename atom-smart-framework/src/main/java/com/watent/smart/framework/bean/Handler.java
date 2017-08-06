package com.watent.smart.framework.bean;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 封装Action信息
 */
@Data
public class Handler {

    /**
     * Controller 信息
     */
    private Class<?> controllerClass;

    /**
     * Action  方法
     */
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }


}
