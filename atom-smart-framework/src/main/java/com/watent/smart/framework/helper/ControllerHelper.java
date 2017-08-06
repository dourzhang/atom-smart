package com.watent.smart.framework.helper;

import com.watent.smart.framework.annotation.Action;
import com.watent.smart.framework.bean.Handler;
import com.watent.smart.framework.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器 帮助类
 */
public class ControllerHelper {


    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isEmpty(controllerClassSet)) {
            return;
        }
        for (Class<?> controllerClass : controllerClassSet) {
            Method[] methods = controllerClass.getDeclaredMethods();
            if (ArrayUtils.isEmpty(methods)) {
                continue;
            }
            for (Method method : methods) {
                if (!method.isAnnotationPresent(Action.class)) {
                    continue;
                }
                Action action = method.getAnnotation(Action.class);
                String mapping = action.value();
                if (!mapping.matches("\\w+/\\w")) {
                    continue;
                }
                String[] array = mapping.split(":");
                if (ArrayUtils.isEmpty(array) || array.length != 2) {
                    continue;
                }
                String requestMethod = array[0];
                String requestPath = array[1];
                Request request = new Request(requestMethod, requestPath);
                Handler handler = new Handler(controllerClass, method);
                ACTION_MAP.put(request, handler);
            }
        }
    }

    /**
     * 获取Handler
     *
     * @param requestMethod 请求方法
     * @param requestPath   请求路径
     * @return Handler
     */
    public static Handler getHandler(String requestMethod, String requestPath) {

        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }

}
