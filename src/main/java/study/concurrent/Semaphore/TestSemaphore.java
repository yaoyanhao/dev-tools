package study.concurrent.Semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by vector01.yao on 2017/6/7.
 */
public class TestSemaphore {
    public static void main(String[] args) {
        ExecutorService threadPool= Executors.newFixedThreadPool(20);
        final Semaphore semaphore=new Semaphore(5);//最多运行5个线程
        for (int i=0;i<20;i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();//获取许可
                        System.out.println(Thread.currentThread().getName()+" is running....");
                        semaphore.release();//归还许可
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
