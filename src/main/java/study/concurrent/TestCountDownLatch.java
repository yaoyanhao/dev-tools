package study.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by vector01.yao on 2017/6/7.
 * CountDownLatch类是java5之后提供的并发工具类，它能够完成类似于join的功能。
 * 即可以让当前线程暂时阻塞，让其他的多个线程或者某个线程的多个步骤先执行完毕，再执行当前线程。
 * 用法：
 * 1.主线程在其他线程启动之后，立刻调用CountDownLatch对象的await方法，从而让主线程阻塞
 * 2.其他线程执行，每执行完毕一个线程，就调用CountdownLatch对象的countDown方法，使计数减1，标识一个线程执行完毕；
 * 3.当CountDownLatch对象计数为0时，主线程之外的线程都执行完毕，主线程继续执行。
 */
public class TestCountDownLatch {
    private static CountDownLatch countDownLatch=new CountDownLatch(2);//2表示要等待完成线程数
    private static CountDownLatch countDownLatch2=new CountDownLatch(1);//
    public static void main(String[] args) throws InterruptedException {

        /**
         * 应用场景1：主线程执行之前，保证其他线程执行完毕，并可以控制其他线程执行顺序
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread 1 is running.....");
                countDownLatch.countDown();//CountDownLatch计数减1
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread 2 is running....");
                countDownLatch.countDown();//CountDownLatch计数减1
            }
        }).start();
        countDownLatch.await();
        System.out.println("all other thread has finished!");

        /**
         * 应用场景2：实现最大的并行性
         * 如我们想同时启动多个线程，让多个线程同时并发，我们只需要实例化一个count为1的CountDownLatch，
         * 在各个线程中调用CountDownLatch的await，等到所有线程启动完成，在外部线程CountDown，让所有线程同时并发
         */
        for (int i=0;i<100;i++){
            new Thread(new InnerClass(),"thread"+i).start();
        }
        countDownLatch2.countDown();//

    }

    static class InnerClass implements Runnable{

        @Override
        public void run() {
            try {
                countDownLatch2.await();//等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"is runnning..");
        }
    }
}
