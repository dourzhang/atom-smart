package com.watent.smart.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流操作工具类
 */
public final class StreamUtil {

    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从流获取字符
     */
    public static String getString(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while (null != (line = reader.readLine())) {
                builder.append(line);
            }
        } catch (IOException e) {
            logger.error("get string failure", e);
            throw new RuntimeException(e);
        }
        return builder.toString();
    }


}
