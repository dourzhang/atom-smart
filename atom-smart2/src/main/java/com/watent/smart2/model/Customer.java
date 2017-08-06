package com.watent.smart2.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 客户
 */
@Data
public class Customer implements Serializable {

    /**
     * ID
     */
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 电话号码
     */
    private String telephone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 备注
     */
    private String remark;
}
