package study.io.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by vector01.yao on 2017/5/26.
 */
public class SelectorTest {
    public static void main(String[] args) {
        Selector selector=null;
        try {
            selector=Selector.open();//创建Selector对象
            SocketChannel socketChannel=SocketChannel.open();//创建SocketChannel对象
            socketChannel.configureBlocking(false);//配置非阻塞方式，非阻塞方式下可以异步调用其connect、read和write方法，channel可能在尚未读写完成时，就会返回，
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));//socketChannel扮演客户端，向指定地址发起连接请求
            System.out.println(socketChannel.isConnected()?"connected!":"no connected!");

            SelectionKey key=socketChannel.register(selector, SelectionKey.OP_READ);

//            while (true){
//                int readyChannel=selector.select();
//                if (readyChannel==-1){
//                    continue;
//                }
//                Set<SelectionKey> selectionKeySet=selector.selectedKeys();
//                for (SelectionKey selectionKey:selectionKeySet){
//                    if (selectionKey.isAcceptable()){
//                        System.out.println("accept");
//                    }else if (selectionKey.isConnectable()){
//                        System.out.println("connect");
//                    }else if (selectionKey.isReadable()){
//                        System.out.println("read");
//                    }else if(selectionKey.isWritable()){
//                        System.out.println("write");
//                    }
//                }
//            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("发生异常!异常信息：{}",e);
        } finally {
            if (selector!=null){
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
