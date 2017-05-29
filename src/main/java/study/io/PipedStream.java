package study.io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by vector01.yao on 2017/5/29.
 * IO管道为同一个JVM中不同线程的通信提供了方式.
 *
 * 注意：在利用管道读写数据时，必须保证利用管道读写数据的线程都不能退出！否则会报Read end dead或Write end dead的错
 * 因此，此处使用了等待-通知机制
 */
public class PipedStream {

    private static boolean isFinished=false;
    static final Object lockObject=new Object();

    public static void main(String[] args) throws IOException {
        final PipedOutputStream pipedOutputStream=new PipedOutputStream();
        final PipedInputStream pipedInputStream=new PipedInputStream();
        pipedInputStream.connect(pipedOutputStream);//使两个读写管道相连接

        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lockObject){
                        pipedOutputStream.write("hello world!".getBytes());
                        while (!isFinished){
                            lockObject.wait();
                        }
                    }
                }catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }

            }
        });
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int data;
                    while ((data=pipedInputStream.read())!=-1){
                        System.out.print((char)data);
                    }
                    isFinished=true;
                    lockObject.notifyAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
