package aop;

import annotation.Transaction;
import helper.TransactionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.MyThreadLocal;

import java.lang.reflect.Method;

public class TransactionProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);

    private static MyThreadLocal<Boolean> currRun = new MyThreadLocal<Boolean>();

    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Method targetMethod = proxyChain.getTargetMethod();
        final Boolean flag = currRun.get();
        if(!flag && targetMethod.isAnnotationPresent(Transaction.class)){
            try {
                TransactionHelper.startTransaction();
                result = proxyChain.doProxyChain();
                TransactionHelper.commitTransaction();
                currRun.set(true);
            } catch (Throwable throwable) {
                LOGGER.error("事务失败，进行回滚",throwable);
                TransactionHelper.rollbackTransaction();
            } finally {
                currRun.remove();
            }
        }else{
            result = proxyChain.doProxyChain();
        }
        return result;
    }



}
