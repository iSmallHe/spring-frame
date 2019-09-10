package helper;


import annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Map;

public class IocHelper {
    static{
        Map<Class<?>, Object> beanmap = BeanHelper.getBeanmap();
        if(beanmap != null){
            for (Map.Entry<Class<?>,Object> entry:beanmap.entrySet()) {
                Object bean = entry.getValue();
                Class<?> beanClass = entry.getKey();
                Field[] declaredFields = beanClass.getDeclaredFields();
                for(Field field : declaredFields){
                    if(field.isAnnotationPresent(Autowired.class)){
                        Class<?> fieldType = field.getType();
                        Object autowireBean = BeanHelper.getBean(fieldType);
                        if(autowireBean != null){
                            ReflectionHelper.setField(bean,field,autowireBean);
                        }
                    }
                }
            }
        }
    }
}
