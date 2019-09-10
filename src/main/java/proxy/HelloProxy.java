package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HelloProxy<T> implements InvocationHandler {

    private T target;

    public HelloProxy(T target){
        this.target = target;
    }

    public <T> T getProxy(){
        return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("haha");
        Class<?>[] parameterTypes = method.getParameterTypes();
        for(Class cls :parameterTypes)
            System.out.println(cls.getName());
        Object result = method.invoke(target, args);
        return result;
    }
}
