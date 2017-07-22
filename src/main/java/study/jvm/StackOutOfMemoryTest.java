package study.jvm;

/**
 * 设置单个线程栈容量为2M：-Xss2M
 * Created by vector01.yao on 2017/7/22.
 * 在多线程下，每个线程独占一个虚拟机栈。
 * 而一个Java进程的总内存是有限制的（一般是2G），在这个Java进程中，除了堆和方法区占用之外，剩下的内存基本都是被各个线程的虚拟机栈“瓜分”，
 * 当我们不停地创建线程，并且每个线程都短时间得不到结束（内存释放不掉），当再新创建线程所需的空间申请不到的时候，将会抛出OutOfMemoryError异常。
 */
public class StackOutOfMemoryTest {
    /**
     * 此程序可能造成操作系统假死
     * @param args
     */
    public static void main(String[] args) {
        while (true){
            Thread thread= new Thread(new NoStopThread());
            thread.start();
        }
    }
}

class NoStopThread implements Runnable{

    @Override
    public void run() {
        while (true){
            //保持线程不结束
        }
    }
}
