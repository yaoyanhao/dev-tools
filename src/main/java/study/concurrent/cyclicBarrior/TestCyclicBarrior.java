package study.concurrent.cyclicBarrior;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by vector01.yao on 2017/6/7.
 * 利用CyclicBarrior实现从1-100的加法，分10个线程
 */
public class TestCyclicBarrior implements Runnable{
    private CyclicBarrier cyclicBarrier=new CyclicBarrier(10,this);
    private ExecutorService threadPool= Executors.newFixedThreadPool(10);
    private Map<String,Integer> resultMap=new HashMap<>();

    public void doCalculator() throws ExecutionException, InterruptedException, BrokenBarrierException {
        for (int i=0;i<10;i++){//10个线程
            final int start=10*i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    int sum=0;
                    for (int j=1;j<=10;j++){
                        sum+=(start+j);
                    }
                    resultMap.put(Thread.currentThread().getName(),sum);
                    try {
                        cyclicBarrier.await();//到达屏障，所有任务都到达屏障时，才继续执行
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void run() {
        //对resultMap中的结果进行汇总
        int sum=0;
        for (Map.Entry<String,Integer> entry:resultMap.entrySet()){
            sum+=entry.getValue();
        }
        System.out.println("result:"+sum);
    }

    public static void main(String[] args) {
        TestCyclicBarrior testCyclicBarrior=new TestCyclicBarrior();
        try {
            testCyclicBarrior.doCalculator();
        } catch (ExecutionException | InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
