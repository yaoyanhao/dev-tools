package study;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vector01.yao on 2017/6/7.
 */
public class TestAtomic {
    //private static int count=0;
    private static AtomicInteger count=new AtomicInteger(0);

    static class TestThread implements Runnable{

        public void run() {
            for (int i=0;i<1000;i++){
               //count++;
                count.getAndIncrement();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1=new Thread(new TestThread());
        Thread thread2=new Thread(new TestThread());
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(count);
    }
}
