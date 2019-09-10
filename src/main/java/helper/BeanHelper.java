package helper;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanHelper {
    private static final Map<Class<?>,Object> BEANMAP = new HashMap<Class<?>,Object>();

    static{
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class clazz: beanClassSet) {
            Object instance = ReflectionHelper.newInstance(clazz);
            BEANMAP.put(clazz,instance);
        }
    }

    public static Map<Class<?> ,Object> getBeanmap(){
        return BEANMAP;
    }

    public static <T> T getBean(Class<T> clazz){
        if(!BEANMAP.containsKey(clazz)){
            throw new RuntimeException("can not get bean by class");
        }
        return (T)BEANMAP.get(clazz);
    }

    public static void setBean(Class<?> clazz,Object obj){
        BEANMAP.put(clazz,obj);
    }
}
