package com.watent.smart.framework;

import com.watent.smart.framework.bean.Data;
import com.watent.smart.framework.bean.Handler;
import com.watent.smart.framework.bean.Param;
import com.watent.smart.framework.bean.View;
import com.watent.smart.framework.helper.*;
import com.watent.smart.framework.util.JsonUtil;
import com.watent.smart.framework.util.ReflectionUtil;
import com.watent.smart.framework.util.StringUtil;
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
        jspServlet.addMapping("/index.jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的默认 Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping("/favicon.ico");
        defaultServlet.addMapping(ConfigHelper.getAppAssertPath() + "*");

        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();

        if (requestPath.equals("/favicon.ico")) {
            return;
        }

        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (null == handler) {
            logger.warn("No mapped handler for : {}", requestPath);
            return;
        }
        Class<?> controllerClass = handler.getControllerClass();
        Object controllerBean = BeanHelper.getBean(controllerClass);

        Param param;
        if (UploadHelper.isMultipart(request)) {
            param = UploadHelper.createParam(request);
        } else {
            param = RequestHelper.createParam(request);
        }

        Object result;
        Method actionMethod = handler.getActionMethod();
        if (param.isEmpty()) {
            result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
        } else {
            result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
        }

        if (result instanceof View) {
            handleViewResult((View) result, request, response);
        } else if (result instanceof Data) {
            handleDataResult((Data) result, response);
        }
    }

    private void handleViewResult(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
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
    }

    private void handleDataResult(Data data, HttpServletResponse response) throws IOException {
        Object model = data.getModel();
        if (model != null) {
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
