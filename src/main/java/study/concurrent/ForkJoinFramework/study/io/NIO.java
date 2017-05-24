package study.concurrent.ForkJoinFramework.study.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by vector01.yao on 2017/5/23.
 * 使用Buffer读入数据大致分为以下4个步骤：
 * 1.将数据通过Channel写入到Buffer中
 * 2.flip()方法：以为翻转===>从写模式切换到读模式
 * 3.从Buffer中将数据读入Channel中（写文件时使用）
 * 4.情况Buffer中的数据：从而可以继续写入数据   clear()：完全清空  compact()：只清除已读取的数据
 */
public class NIO {
    public static void main(String[] args) {
        File file=new File("D:\\NIO.txt");
        File targetFile=new File("D:\\NIO_CP.txt");
        try {
            FileChannel fileChannel=new FileInputStream(file).getChannel();
            FileChannel outChannel=new FileOutputStream(targetFile).getChannel();
            ByteBuffer byteBuffer=ByteBuffer.allocate(10);//分配Buffer空间
            int readTag=fileChannel.read(byteBuffer);//1.将文件数据通过Channel写入Buffer，无数据可写时为-1
            while (readTag!=-1){
                byteBuffer.flip();//2.从写模式切换到读模式====>开始准备读咯
                while (byteBuffer.hasRemaining()){//当缓存区中还有“余粮”，就不停读.....
                    System.out.print(byteBuffer.get());
                }
                byteBuffer.rewind();//buffer倒回=====>position重置为0，实现Buffer的重复读取
                outChannel.write(byteBuffer);//读取buffer中的数据，写入输出channal中
                byteBuffer.clear();//清空Buffer
                readTag=fileChannel.read(byteBuffer);//再次写入Buffer
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
