package study.concurrent.threadlocal;

/**
 * Created by vector01.yao on 2017/6/29.
 */
public class TestThreadLocal {
    private static ThreadLocal<Integer> threadLocal=new ThreadLocal<Integer>(){
        @Override
        public Integer initialValue(){
            return 0;
        }
    };

    public static void main(String[] args) {
        threadLocal.set(100);
        System.out.println(threadLocal.get());
    }
}
