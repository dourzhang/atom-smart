package com.watent.smart.framework.bean;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回视图对象
 */
public class View {

    /**
     * 视图路径
     */
    @Getter
    private String path;
    /**
     * 模型数据
     */
    @Getter
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        this.model = new HashMap<>();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }
}
