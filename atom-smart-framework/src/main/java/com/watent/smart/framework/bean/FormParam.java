package com.watent.smart.framework.bean;

import lombok.Getter;

/**
 * 封装表单参数
 */
@Getter
public class FormParam {

    private String fieldName;

    private Object fieldValue;

    public FormParam(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
