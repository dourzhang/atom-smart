package com.watent.smart.framework;

import com.watent.smart.framework.bean.Data;
import com.watent.smart.framework.bean.Handler;
import com.watent.smart.framework.bean.Param;
import com.watent.smart.framework.bean.View;
import com.watent.smart.framework.helper.BeanHelper;
import com.watent.smart.framework.helper.ConfigHelper;
import com.watent.smart.framework.helper.ControllerHelper;
import com.watent.smart.framework.helper.HelpLoader;
import com.watent.smart.framework.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化相关help类
        HelpLoader.init();
        // 获取ServletContext对象 注册Servlet
        ServletContext servletContext = servletConfig.getServletContext();
        //注册处理Jsp的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的默认 Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping("/favicon.ico");
        defaultServlet.addMapping(ConfigHelper.getAppAssertPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();

        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (null == handler) {
            logger.warn("No mapped handler for : {}", requestPath);
            return;
        }
        Class<?> controllerClass = handler.getControllerClass();
        Object controllerBean = BeanHelper.getBean(controllerClass);
        //创建请求参数对象
        Map<String, Object> paramMap = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            paramMap.put(paramName, paramValue);
        }
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if (StringUtil.isNotEmpty(body)) {
            String[] params = StringUtil.splitString(body, "&");
            if (ArrayUtil.isEmpty(params)) {
                for (String param : params) {
                    String[] array = StringUtil.splitString(param, "=");
                    if (ArrayUtil.isEmpty(array) || array.length != 2) {
                        continue;
                    }
                    String paramName = array[0];
                    String paramValue = array[1];
                    paramMap.put(paramName, paramValue);
                }
            }

        }
        Param param = new Param(paramMap);
        //调用Action方法
        Method actionMethod = handler.getActionMethod();
        Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
        //处理Action返回值
        if (result instanceof View) {
            //返回JSP
            View view = (View) result;
            String path = view.getPath();
            if (StringUtil.isEmpty(path)) {
                return;
            }
            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
            }
        }
        if (result instanceof Data) {
            Data data = (Data) result;
            Object model = data.getModel();
            if (null == model) {
                return;
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}
