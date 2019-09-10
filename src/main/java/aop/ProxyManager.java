package aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

public class ProxyManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyManager.class);

    public static Object createProxy(final Class<?> clazz,final List<Proxy> proxyList){
        LOGGER.debug("createProxy");
        return Enhancer.create(clazz,new MethodInterceptor(){
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(clazz,o,method,methodProxy,objects,proxyList).doProxyChain();
            }
        });
    }
}
