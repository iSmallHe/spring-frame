package servlet;

import bean.Data;
import bean.Handler;
import bean.Param;
import bean.View;
import helper.*;
import org.apache.commons.lang3.StringUtils;
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

@WebServlet(value = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);
    @Override
    public void init(ServletConfig config) throws ServletException {
        FrameLoadHelper.init();
        ServletContext servletContext = config.getServletContext();
        ServletRegistration jsp = servletContext.getServletRegistration("jsp");
        jsp.addMapping(ConfigHelper.VIEWPATH+"*");
        ServletRegistration asset = servletContext.getServletRegistration("default");
        asset.addMapping(ConfigHelper.ASSETPATH+"*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("正在运行,"+req.getRequestURL());
        String requestMethod = req.getMethod().toLowerCase();
        String pathInfo = req.getPathInfo();
        LOGGER.debug("地址："+pathInfo+",请求方式"+requestMethod);
        Handler handler = ControllerHelper.getHandler(pathInfo, requestMethod);
        if(handler != null){
            LOGGER.debug("存在处理器");
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            Param param = null;
            Method requestMappingMethod = handler.getRequestMappingMethod();
            if(requestMappingMethod.getParameterTypes().length != 0){
                LOGGER.debug("有参方法");
                Map<String,Object> params = new HashMap<String,Object>();
                Enumeration<String> parameterNames = req.getParameterNames();
                while(parameterNames.hasMoreElements()){
                    String paramName = parameterNames.nextElement();
                    String paramValue = req.getParameter(paramName);
                    params.put(paramName,paramValue);
                }
                String body = CodeHelper.decodeURL(StreamHelper.getString(req.getInputStream()));
                if(StringUtils.isNotBlank(body)){
                    String[] split = body.split("&");
                    for(String s : split){
                        String[] p = s.split("=");
                        if(p != null && p.length == 2){
                            params.put(p[0],p[1]);
                        }
                    }
                }
                param = new Param(params);
            }
            LOGGER.debug("param="+param);
            Object result = null;
            if(param == null){
                result = ReflectionHelper.invokeMethod(controllerBean, requestMappingMethod, null);
            }else{
                result = ReflectionHelper.invokeMethod(controllerBean, requestMappingMethod, param);
            }

            LOGGER.debug("反射调用后的结果="+result.toString()+",class="+result.getClass().getName());
            if(result instanceof View){
                View view = (View) result;
                String path = view.getPath();
                LOGGER.debug("即将返回view，地址："+path);
                if(StringUtils.isNotBlank(path)){
                    if(path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath()+ path);
                    }else{
                        Map<String, Object> model = view.getModel();
                        for(Map.Entry<String,Object> entry:model.entrySet()){
                            req.setAttribute(entry.getKey(),entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.VIEWPATH + path).forward(req,resp);
                    }
                }
            } else if(result instanceof Data){
                Data data = (Data) result;
                Object model = data.getModel();
                LOGGER.debug("即将返回data，数据："+JsonHelper.beanToJson(model));
                if(model != null){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String jsonBean = JsonHelper.beanToJson(model);
                    writer.write(jsonBean);
                    writer.flush();
                    writer.close();
                }
            }
        }else{
            LOGGER.debug("没有处理器");
        }
    }
}
