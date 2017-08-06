package study.concurrent.threadlocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vector01.yao on 2017/8/5.
 * 自己实现ThreadLocal
 */
public class MyThreadLocal<T> {
    private Map<Thread,T> container= Collections.synchronizedMap(new HashMap<Thread, T>());

    public void set(T value){
        container.put(Thread.currentThread(),value);
    }

    public T get(){
        Thread thread=Thread.currentThread();
        T value=container.get(thread);
        if (value==null&&!container.containsKey(thread)){
            value=initValue();
            container.put(thread,value);
        }
        return value;
    }

    public void remove(){
        container.remove(Thread.currentThread());
    }

    protected T initValue(){
        return null;
    }

}
