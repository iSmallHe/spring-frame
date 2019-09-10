package helper;


import annotation.Controller;
import annotation.Service;
import loader.SpringTestClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class ClassHelper {
    private static final Set<Class<?>> clazzSet;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassHelper.class);
    static{
        String basePackage = ConfigHelper.PACKAGE;
        LOGGER.debug("basePackage="+basePackage);
        clazzSet = SpringTestClassLoader.getClassSet(basePackage);
    }

    public static Set<Class<?>> getClassSet(){
        return clazzSet;
    }

    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> serviceClassSet = new HashSet<Class<?>>();
        for (Class clazz:clazzSet) {
            if(clazz.isAnnotationPresent(Service.class)){
                serviceClassSet.add(clazz);
            }
        }
        return serviceClassSet;
    }

    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> controllerClassSet = new HashSet<Class<?>>();
        for (Class clazz:clazzSet) {
            if(clazz.isAnnotationPresent(Controller.class)){
                controllerClassSet.add(clazz);
            }
        }
        return controllerClassSet;
    }

    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getControllerClassSet());
        beanClassSet.addAll(getServiceClassSet());
        return beanClassSet;
    }

    public static Set<Class<?>>  getClassSetBySuperClass(Class<?> superClass){
        Set<Class<?>> annSet = new HashSet<Class<?>>();
        for(Class cls : clazzSet){
            if(superClass.isAssignableFrom(cls) && !superClass.equals(cls)){
                annSet.add(cls);
            }
        }
        return annSet;
    }


    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> clazz){
        Set<Class<?>> annSet = new HashSet<Class<?>>();
        for(Class cls : clazzSet){
            if(cls.isAnnotationPresent(clazz)){
                annSet.add(cls);
            }
        }
        return annSet;
    }
}
