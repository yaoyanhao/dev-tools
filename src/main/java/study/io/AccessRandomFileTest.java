package study.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by vector01.yao on 2017/5/29.
 */
public class AccessRandomFileTest {
    public static void main(String[] args) {
        try {
            RandomAccessFile randomAccessFile=new RandomAccessFile(new File("D:\\NIO.txt"),"rw");
            String line;
            while ((line=randomAccessFile.readLine())!=null){
                System.out.println(line);
                System.out.println("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
