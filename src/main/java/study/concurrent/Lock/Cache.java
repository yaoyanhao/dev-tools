package study.concurrent.Lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by vector01.yao on 2017/5/20.
 * 组合一个非线程安全的HashMap作为缓存的实现，并利用读写锁来保证缓存的线程安全
 */
public class Cache {
    static Map<String,Object> map=new HashMap<String, Object>();
    static ReentrantReadWriteLock reentrantReadWriteLock=new ReentrantReadWriteLock();
    private static Lock readLock=reentrantReadWriteLock.readLock();
    private static Lock writeLock=reentrantReadWriteLock.writeLock();

    public static final Object get(String key){
        readLock.lock();
        try {
            return map.get(key);
        }finally {
            readLock.unlock();
        }
    }

    public static final void set(String key,Object value){
        writeLock.lock();
        try {
            map.put(key,value);
        }finally {
            writeLock.unlock();
        }
    }

    public static final void clear(){
        writeLock.lock();
        try {
            map.clear();
        }finally {
            writeLock.unlock();
        }
    }
}
