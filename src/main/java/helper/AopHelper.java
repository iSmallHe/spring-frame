package helper;

import annotation.Aspect;
import annotation.Service;
import annotation.Transaction;
import aop.AspectProxy;
import aop.Proxy;
import aop.ProxyManager;
import aop.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

public class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);


    static{
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            LOGGER.debug("proxyMap="+proxyMap.size()+",targetMap="+targetMap.size());
            for(Map.Entry<Class<?>,List<Proxy>> entry:targetMap.entrySet()){
                Class<?> key = entry.getKey();
                List<Proxy> value = entry.getValue();
                Object proxy = ProxyManager.createProxy(key, value);
                BeanHelper.setBean(key,proxy);
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("AopHelper failure",e);
        } catch (InstantiationException e) {
            LOGGER.error("AopHelper failure",e);
        } catch (Exception e) {
            LOGGER.error("AopHelper failure",e);
        }
    }

    public static Set<Class<?>> createTargetClassSet(Aspect aspect){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && !annotation.equals(Aspect.class)){
            classSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return classSet;
    }


    public static Map<Class<?>,Set<Class<?>>> createProxyMap(){
        Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<Class<?>,Set<Class<?>>>();
        createAspectProxyMap(proxyMap);
        createTransactionProxyMap(proxyMap);
        return proxyMap;
    }

    public static void createAspectProxyMap(Map<Class<?>,Set<Class<?>>> map){
        Set<Class<?>> classSetBySuperClass = ClassHelper.getClassSetBySuperClass(AspectProxy.class);
        for(Class clazz : classSetBySuperClass){
            if(clazz.isAnnotationPresent(Aspect.class)){
                Aspect annotation = (Aspect) clazz.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(annotation);
                map.put(clazz,targetClassSet);
                LOGGER.debug("proxyMap添加成功="+clazz.getName());
            }
        }
    }

    public static void createTransactionProxyMap(Map<Class<?>,Set<Class<?>>> map){
        Set<Class<?>> classSetByAnnotation = ClassHelper.getClassSetByAnnotation(Service.class);
        map.put(TransactionProxy.class,classSetByAnnotation);
    }

    public static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws IllegalAccessException, InstantiationException {
        Map<Class<?>,List<Proxy>> targetMap = new HashMap<Class<?>,List<Proxy>>();
        for(Map.Entry<Class<?>,Set<Class<?>>> entry : proxyMap.entrySet()){
            Class<?> key = entry.getKey();
            Set<Class<?>> value = entry.getValue();
            Proxy proxy = (Proxy) key.newInstance();
            for(Class<?> clazz : value){
                if(targetMap.containsKey(clazz)){
                    targetMap.get(clazz).add(proxy);
                }else{
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    targetMap.put(clazz,proxyList);
                    proxyList.add(proxy);
                }
            }
        }
        return targetMap;
    }

}
