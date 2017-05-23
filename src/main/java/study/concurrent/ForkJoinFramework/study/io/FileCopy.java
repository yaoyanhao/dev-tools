package study.concurrent.ForkJoinFramework.study.io;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by vector01.yao on 2017/5/23.
 * 单线程下，实现文件复制
 */
public class FileCopy {

    public static void main(String[] args) {
        FileCopy fileCopy=new FileCopy();
        //fileCopy.bufferedReaderCopy("D:\\模板038.pptx","E:",null);

        //fileCopy.bufferedStreamCopy("D:\\模板038.pptx","E:",null);


        fileCopy.fileChannelCopy("D:\\模板038.pptx","E:",null);
        System.out.println("复制文件成功!");
    }

    /**
     * FileInputStream方式复制文件
     * @param sourceFilePath 源文件地址
     * @param targetPath 目标目录
     * @param alias 别名
     */
    public void bufferedReaderCopy(String sourceFilePath,String targetPath,String alias){

        //参数验证
        if (StringUtils.isBlank(sourceFilePath)||StringUtils.isBlank(targetPath)){
            throw new RuntimeException("参数sourceFile或targetDir不能为空！");
        }
        File dir=new File(targetPath);//检查目录是否存在，否则创建目录
        if (!dir.exists()){
            dir.mkdir();
        }

        //
        String fileName=sourceFilePath.substring(sourceFilePath.indexOf("\\"));//文件名
        File inFile=new File(sourceFilePath);
        String targetFileStr=targetPath+"\\";//目标文件
        if (StringUtils.isBlank(alias)){
            targetFileStr+=fileName;
        }else {
            targetFileStr+=alias;
        }
        File outFile=new File(targetFileStr);

        BufferedReader bufferedReader=null;
        BufferedWriter bufferedWriter=null;
        try {
            bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(inFile),"gbk"));
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"gbk"));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                bufferedWriter.write(line+"\r\n");//换行时，必须\r\n，否则无效
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("找不到文件！，请检查文件路径");
        } catch (IOException e) {
            throw new RuntimeException("IO异常："+e.getMessage());
        }finally {
            dispose(bufferedReader);
            dispose(bufferedWriter);
        }
    }

    private void dispose(Reader reader){
        try {
            if (reader!=null){
                reader.close();
            }
        }catch (IOException e){
            throw new RuntimeException("关闭Reader失败！");
        }
    }

    private void dispose(Writer writer){
        try {
            if (writer!=null){
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------

    public void bufferedStreamCopy(String sourceFilePath,String targetPath,String alias){
        //参数验证
        if (StringUtils.isBlank(sourceFilePath)||StringUtils.isBlank(targetPath)){
            throw new RuntimeException("参数sourceFile或targetDir不能为空！");
        }
        File dir=new File(targetPath);//检查目录是否存在，否则创建目录
        if (!dir.exists()){
            dir.mkdir();
        }

        //
        String fileName=sourceFilePath.substring(sourceFilePath.indexOf("\\"));//文件名
        File inFile=new File(sourceFilePath);
        String targetFileStr=targetPath+"\\";//目标文件
        if (StringUtils.isBlank(alias)){
            targetFileStr+=fileName;
        }else {
            targetFileStr+=alias;
        }
        File outFile=new File(targetFileStr);

        BufferedInputStream bufferedInputStream=null;
        BufferedOutputStream bufferedOutputStream=null;
        try {
            bufferedInputStream=new BufferedInputStream(new FileInputStream(inFile));
            bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(outFile));
            int b;
            while ((b=bufferedInputStream.read())!=-1){
                bufferedOutputStream.write(b);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dispose(bufferedInputStream);
            dispose(bufferedOutputStream);
        }
    }

    private void dispose(InputStream inputStream){
        if (inputStream!=null){
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispose(OutputStream outputStream){
        if (outputStream!=null){
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //FileChannel方式

    public void fileChannelCopy(String sourceFilePath,String targetPath,String alias){
        //参数验证
        if (StringUtils.isBlank(sourceFilePath)||StringUtils.isBlank(targetPath)){
            throw new RuntimeException("参数sourceFile或targetDir不能为空！");
        }
        File dir=new File(targetPath);//检查目录是否存在，否则创建目录
        if (!dir.exists()){
            dir.mkdir();
        }

        //
        String fileName=sourceFilePath.substring(sourceFilePath.indexOf("\\"));//文件名
        File inFile=new File(sourceFilePath);
        String targetFileStr=targetPath+"\\";//目标文件
        if (StringUtils.isBlank(alias)){
            targetFileStr+=fileName;
        }else {
            targetFileStr+=alias;
        }
        File outFile=new File(targetFileStr);

        FileChannel inChannel;
        FileChannel outChannel;
        try {
            inChannel=new FileInputStream(inFile).getChannel();
            outChannel=new FileOutputStream(outFile).getChannel();
            ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
//            while (inChannel.read(byteBuffer)!=-1){//将数据倒到缓冲器中
//                byteBuffer.flip();//准备写入
//                outChannel.write(byteBuffer);//将buffer中的数据先写入
//                byteBuffer.clear();//writer操作之后，数据仍然存在于byteBuffer中，清空
//            }
            inChannel.transferTo(0,inChannel.size(),outChannel);
            inChannel.close();
            outChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
