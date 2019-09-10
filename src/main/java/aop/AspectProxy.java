package aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);


    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> clazz = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        begin();
        try{
            if(interceptor(clazz,method,params)){
                before(clazz,method,params);
                result = proxyChain.doProxyChain();
                after(clazz,method,params);
            }else{
                result = proxyChain.doProxyChain();
            }
        }catch(Throwable throwable){
            error(clazz,method,params);
        }finally{
            end(clazz,method,params);
        }
        return result;
    }

    public void begin(){

    }

    public boolean interceptor(Class<?> clazz, Method method,Object[] params) throws Throwable{
        return true;
    }

    public void before(Class<?> clazz, Method method,Object[] params) throws Throwable{

    }

    public void after(Class<?> clazz, Method method,Object[] params) throws Throwable{

    }

    public void error(Class<?> clazz, Method method,Object[] params) throws Throwable{

    }

    public void end(Class<?> clazz, Method method,Object[] params) throws Throwable{

    }
}
