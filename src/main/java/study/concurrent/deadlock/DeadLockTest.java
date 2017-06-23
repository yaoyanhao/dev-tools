package study.concurrent.deadlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vector01.yao on 2017/6/21.
 * 模拟死锁
 */
public class DeadLockTest extends Thread{
    private Logger logger= LoggerFactory.getLogger(DeadLockTest.class);

    private final Object lockObject1;
    private final Object lockObject2;
    public DeadLockTest(Object object1,Object object2){
        lockObject1=object1;
        lockObject2=object2;
    }

    @Override
    public void run(){
        synchronized (lockObject1){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockObject2){
                logger.error("do something....");
            }
        }
    }

    public static void main(String[] args) {
        Object lock1=new Object();
        Object lock2=new Object();
        Thread thread1=new DeadLockTest(lock1,lock2);
        Thread thread2=new DeadLockTest(lock2,lock1);
        thread1.start();
        thread2.start();
    }
}
