package com.watent.smart.framework.bean;

import lombok.Getter;

import java.io.InputStream;

/**
 * 封装上传文件参数
 */
@Getter
public class FileParam {

    /**
     * 文件表单字段名
     */
    private String fieldName;
    /**
     * 上传文件的文件名
     */
    private String fileName;
    /**
     * 文件的大小
     */
    private long fileSize;
    /**
     * 文件类型
     */
    private String contentType;
    /**
     * 上传文件字节流
     */
    private InputStream inputStream;

    public FileParam(String fieldName, String fileName, long fileSize, String contentType, InputStream inputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }
}
