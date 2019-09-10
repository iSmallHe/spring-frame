package proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGlibProxy implements MethodInterceptor {

    public <T> T getProxy(Class<T> clazz){
        return (T)Enhancer.create(clazz,this);
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("CG礼包");
//        Object o1 = methodProxy.invokeSuper(o, objects);
        return null;
    }
}
