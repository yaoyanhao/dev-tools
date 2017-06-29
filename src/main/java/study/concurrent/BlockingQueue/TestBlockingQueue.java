package study.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by vector01.yao on 2017/6/29.
 */
public class TestBlockingQueue {
    private static Logger logger= LoggerFactory.getLogger(TestBlockingQueue.class);
    private static Random random=new Random(47);
    private static AtomicInteger start=new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService= Executors.newCachedThreadPool();
        BlockingQueue<Integer> blockingQueue=new ArrayBlockingQueue<Integer>(1000);
        for (int i=1;i<=5;i++){//5个生产者
            executorService.submit(new Productor("productor"+i,blockingQueue));
        }
        for (int j=1;j<=5;j++){//5个消费者
            executorService.submit(new Consumer("consumer"+j,blockingQueue));
        }
    }

    static class Productor implements Runnable{
        private String name;
        private BlockingQueue blockingQueue;

        Productor(String name,BlockingQueue blockingQueue){
            this.name=name;
            this.blockingQueue=blockingQueue;
        }

        @Override
        public void run() {
            for (int i=0;i<10;i++){
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                    int putValue=start.getAndIncrement();
                    blockingQueue.add(putValue);
                    logger.error("生产者放入队列====>"+putValue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.error(name+" has finished work!");
        }
    }

    static class Consumer implements Runnable{
        private String name;
        private BlockingQueue blockingQueue;

        Consumer(String name,BlockingQueue blockingQueue){
            this.name=name;
            this.blockingQueue=blockingQueue;
        }

        @Override
        public void run() {
            for (int i=0;i<10;i++){
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                    logger.error("消费者取出数据====>"+blockingQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.error(name+" has finished work!");
        }
    }
}
