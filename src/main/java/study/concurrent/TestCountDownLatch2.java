package study.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by vector01.yao on 2017/6/7.
 * 典型应用1：实现最大的并行性
 * 1.线程启动时先阻止其运行（startSignal.await）,等主线程准备好之后再执行
 * 2.endSignal各分线程的执行完成标识,每执行完成一个，countDown一次，保证所有线程任务都执行完毕
 */
public class TestCountDownLatch2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal=new CountDownLatch(1);//控制任务什么时候开始
        CountDownLatch endSignal=new CountDownLatch(100);//保证任务全部执行完成

        for (int i=0;i<100;i++){
            new Thread(new Worker(startSignal,endSignal)).start();
        }
        doBefore();
        startSignal.countDown();//统一开始任务
        endSignal.await();//保证100个线程执行完毕后，再退出
        System.out.println("所有任务都已经执行完毕！");
        doAfter();
    }

    private static void doBefore(){
        System.out.println("before task....");
    }

    private static void doAfter(){
        System.out.println("after task.....");
    }
}

class Worker implements Runnable{
    private CountDownLatch startSignal;
    private CountDownLatch endSignal;

    Worker(CountDownLatch startSignal,CountDownLatch endSignal){
        this.startSignal=startSignal;
        this.endSignal=endSignal;
    }

    @Override
    public void run() {
        try {
            startSignal.await();//先用startSignal阻止所有任务的执行，等待startSignal的count为0的那一刻
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doWork();
        endSignal.countDown();//一个任务已经搞定
    }

    private void doWork(){
        System.out.println(Thread.currentThread().getName()+" is running.....");
    }
}
