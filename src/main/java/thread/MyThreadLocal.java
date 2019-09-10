package thread;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal<T> {

    private final Map<Thread,T> threadLocalMap = Collections.synchronizedMap(new HashMap<Thread,T>());

    public void set(T value){
        Thread thread = Thread.currentThread();
        threadLocalMap.put(thread,value);
    }

    public T get(){
        Thread thread = Thread.currentThread();
        T t = threadLocalMap.get(thread);
        if(t == null && !threadLocalMap.containsKey(thread)){
            t = initialValue();
            threadLocalMap.put(thread,t);
        }
        return t;
    }

    public void remove(){
        threadLocalMap.remove(Thread.currentThread());
    }

    protected T initialValue(){
        return null;
    }
}
