package helper;


import annotation.RequestMapping;
import bean.Handler;
import bean.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class ControllerHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHelper.class);

    private static final Map<Request, Handler> mappingMap = new HashMap<Request,Handler>();

    static{
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        for(Class<?> clazz : controllerClassSet){
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for(Method method :declaredMethods){
                if(method.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping rm = method.getAnnotation(RequestMapping.class);
                    String requestInfo = rm.value();
                    if(requestInfo != null && requestInfo.matches("\\w+:/\\w*")){
                        String[] split = requestInfo.split(":");
                        if(split!= null && split.length == 2){
                            String requestMethod = split[0];
                            String requestPath = split[1];
                            Request request = new Request(requestPath,requestMethod);
                            Handler handler = new Handler(clazz,method);
                            mappingMap.put(request,handler);
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestPath, String requestMethod){
        Request request = new Request(requestPath,requestMethod);
        return getHandler(request);
    }

    public static Handler getHandler(Request request){
        return mappingMap.get(request);
    }
}
