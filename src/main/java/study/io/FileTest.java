package study.io;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by vector01.yao on 2017/5/29.
 * File类：获取文件或目录的信息
 */
public class FileTest {
    public static void main(String[] args) {
        //File获取目录信息
        File dir=new File("D:");
        if (!dir.exists()&&dir.isDirectory()){//若目录不存在则创建目录
            dir.mkdir();
        }
        //列出目录下所有文件及目录
        File[] fileList=dir.listFiles();
        System.out.println("目录"+dir.getAbsolutePath()+"下，所有文件：");
        for (File file:fileList){
            System.out.println(file.getName());
        }

        //列出目录下满足指定条件的文件及目录
        File[] fileWithCondition=dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith("pptx")){
                    return  true;
                }
                return false;
            }
        });
        System.out.println("目录"+dir.getAbsolutePath()+"下，所有ppt文件：");
        for (File file:fileWithCondition){
            System.out.println(file.getName());
        }


        //File获取文件信息
        File file=new File("D:\\abc.txt");
        if (!file.exists() && file.isFile()){
            try {
                dir.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
