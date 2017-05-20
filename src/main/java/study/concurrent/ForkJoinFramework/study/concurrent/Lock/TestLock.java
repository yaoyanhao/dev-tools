package study.concurrent.ForkJoinFramework.study.concurrent.Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by vector01.yao on 2017/5/19.
 */
public class TestLock implements Lock{

    private  final  Sysc sysc=new Sysc();
    /**
     * 1.声明静态内部类继承AbstractQueuedSynchronizer，并重写同步器提供的方法
     * 2.同步器内可以重写的方法有：tryAcquire（独占式获取锁）、tryRelease（独占式释放锁）、
     * tryAcquireShared（共享式获取锁）、tryRelease（共享式释放锁）、isHeldExclusively（当前同步器是否在独占模式下被线程占用）
     * 另外，同步器内重写方法时，需要使用同步器的如下三个方法：getState、setState、compareAndSetState,用于操纵同步状态
     * 3.TestLock中将会调用同步器提供的模板方法。同步器提供的模板方法大致分为3类：独占式获取和释放同步状态、共享式获取和释放同步状态
     */

    private static class Sysc extends AbstractQueuedSynchronizer{

        //当状态为0时获取锁
        @Override
        protected boolean tryAcquire(int arg) {
            //compareAndSetState(expect,update)的语义为：如果当前状态为expect,则更新为update，成功返回true，失败返回false
            if (compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getState()==0){
                throw new IllegalStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        //是否处于占用状态
        @Override
        protected boolean isHeldExclusively() {
            return getState()==1;
        }
    }

    @Override
    public void lock() {
        sysc.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sysc.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sysc.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sysc.tryAcquireNanos(1,time);
    }

    @Override
    public void unlock() {
        sysc.release(0);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

}
