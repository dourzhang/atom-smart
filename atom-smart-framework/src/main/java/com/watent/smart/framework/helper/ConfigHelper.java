package com.watent.smart.framework.helper;

import com.watent.smart.framework.util.ConfigConstant;
import com.watent.smart.framework.util.PropsUtil;

import java.util.Properties;

/**
 * 属性文件工具类
 */
public class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 驱动
     *
     * @return driver
     */
    public static String getJdbcDriver() {

        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * URL
     *
     * @return URL
     */
    public static String getJdbcUrl() {

        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * Username
     *
     * @return Username
     */
    public static String getJdbcUsername() {

        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * Username
     *
     * @return Username
     */
    public static String getJdbcPassword() {

        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * BasePackage
     *
     * @return BasePackage
     */
    public static String getAppBasePackage() {

        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * JspPath
     *
     * @return JspPath
     */
    public static String getAppJspPath() {

        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH);
    }

    /**
     * AssertPath
     *
     * @return AssertPath
     */
    public static String getAppAssertPath() {

        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH);
    }

    /**
     * 获取应用上传限制
     */
    public static int getAppUploadLimit(){
        return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.APP_UPLOAD_LIMIT, 10);
    }

}
