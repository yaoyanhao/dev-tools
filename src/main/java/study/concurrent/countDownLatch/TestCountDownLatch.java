package study.concurrent.countDownLatch;

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
    /**
     * 大任务拆分
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(10);
        for (int i=0;i<10;i++){//假设一个任务分为10个部分
            new Thread(new DepartWorker(countDownLatch,i)).start();
        }
        countDownLatch.await();//等待各个子任务都完成
        System.out.println("所有任务都已完成");
    }
}

//处理
class DepartWorker implements Runnable{
    private int partId;
    private CountDownLatch countDownLatch;

    DepartWorker(CountDownLatch countDownLatch,int partId){
        this.countDownLatch=countDownLatch;
        this.partId=partId;
    }

    @Override
    public void run() {
        doWork(partId);
        countDownLatch.countDown();
    }

    private void doWork(int partId){
        System.out.println("正在处理第"+partId+"部分....");
    }
}
