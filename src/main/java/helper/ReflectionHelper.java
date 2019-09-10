package helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionHelper.class);

    /**
     * 反射创建对象
     * @param clazz
     * @return
     */
    public static Object newInstance(Class<?> clazz){
        Object ob = null;
        try {
            ob = clazz.newInstance();
        } catch (InstantiationException e) {
            LOGGER.error("reflection newInstance failue",e);
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            LOGGER.error("reflection newInstance failue",e);
            throw new RuntimeException();
        }
        return ob;
    }

    /**
     * 反射调用方法
     * @param object
     * @param method
     * @param param
     * @return
     */
    public static Object invokeMethod(Object object, Method method, Object... param){
        Object result = null;
        method.setAccessible(true);
        try {
            LOGGER.debug("方法名称="+method.getName()+",参数="+param);
            result = method.invoke(object,param);
        } catch (IllegalAccessException e) {
            LOGGER.error("reflection invokeMethod failure",e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            LOGGER.error("reflection invokeMethod failure",e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 反射设置属性值
     * @param object
     * @param field
     * @param value
     */
    public static void setField(Object object, Field field, Object value){
        field.setAccessible(true);
        try {
            field.set(object,value);
        } catch (IllegalAccessException e) {
            LOGGER.error("reflection setField failure",e);
            throw new RuntimeException();
        }
    }
}
