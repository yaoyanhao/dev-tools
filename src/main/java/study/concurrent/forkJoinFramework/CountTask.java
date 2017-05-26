package study.concurrent.forkJoinFramework;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by vector01.yao on 2017/5/19.
 * 使用ForkJoin框架，必须首先创建一个ForkJoinTask任务，我们只需要继承ForkJoinTask类的子类即可。
 * ForkJoinTask提供了两个子类：
 * 1.RecursiveTask：有返回值
 * 2.RecursiveAction：无返回值
 *
 */
public class CountTask extends RecursiveTask<Integer> {//RecursiveTask（递归任务）:有返回值

    private static final int THRESHOLD=2;
    private int start;
    private int end;
    public CountTask(int start,int end){
        this.start=start;
        this.end=end;
    }

    protected Integer compute() {
        int sum=0;
        if (end-start<THRESHOLD){
            for (int i=start;i<=end;i++){
                sum+=i;
            }
        }else {
            //将任务分裂为两个子任务
            int middle=(start+end)/2;
            CountTask leftTask=new CountTask(start,middle);
            CountTask rightTask=new CountTask(middle+1,end);
            //执行子任务
            leftTask.fork();
            rightTask.fork();

            //等待任务执行完毕
            int left=leftTask.join();
            int right=rightTask.join();

            sum=left+right;
        }
        return sum;
    }

    public static void main(String[] args) {
        //ForkJoinTask必须通过ForkJoinPool执行！
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        CountTask countTask=new CountTask(1,100);
        Future<Integer> future=forkJoinPool.submit(countTask);
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
