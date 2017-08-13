package com.watent.smart.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Json 工具类
 */
public final class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * pojo 转 Json
     */
    public static <T> String toJson(T obj) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("get string failure", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * Json 转 pojo
     */
    public static <T> T fromJson(String json, Class<T> type) {

        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            logger.error("get string failure", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }


}
