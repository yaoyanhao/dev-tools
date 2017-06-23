package study.concurrent.interrupt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.concurrent.deadlock.DeadLockTest;

/**
 * Created by vector01.yao on 2017/6/21.
 * 测试中断
 */
public class InterruptTest extends Thread {
    private static Logger logger= LoggerFactory.getLogger(InterruptTest.class);

    public static void main(String[] args) throws InterruptedException {
        Thread workThread=new InterruptTest();
        workThread.start();
        Thread.sleep(10);
        workThread.interrupt();//1.中断线程，将中断标志位设置为true

        //测试死锁能否被中断
        logger.error("测试interrupt能否中断死锁");
        Object object1=new Object();
        Object object2=new Object();
        DeadLockTest deadLockTest1=new DeadLockTest(object1,object2);
        DeadLockTest deadLockTest2=new DeadLockTest(object2,object1);
        deadLockTest1.start();
        deadLockTest2.start();
        deadLockTest1.interrupt();
    }

//    /**
//     * 非阻塞下的中断线程
//     */
//    @Override
//    public void run(){
//        while (!Thread.currentThread().isInterrupted()){//检查到了中断标志位true，
//            logger.error("thread is running!");
//        }
//        logger.error("thread is interrupted!");
//    }

    /**
     * 线程阻塞时被中断
     */
    @Override
    public void run(){
        while (!Thread.currentThread().isInterrupted()){
            try {
                while (true){
                    logger.error("thread is running....");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {//线程阻塞时，被中断，会抛出异常
                logger.error("线程阻塞时被中断，当前中断状态：{}",Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();//重新设置中断状态,如果不重新设置，线程将无法中断
                logger.error("重新设置中断状态，当前中断状态：{}",Thread.currentThread().isInterrupted());
            }
        }
    }
}
