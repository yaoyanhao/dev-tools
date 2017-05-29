package study.io.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
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
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8000));//socketChannel扮演客户端，向指定地址发起连接请求
            System.out.println(socketChannel.isConnected()?"connected!":"no connected!");

//            SelectionKey key=socketChannel.register(selector, SelectionKey.OP_READ);//SelectionKey对象包含了Channel对象、Selector对象，interest集合以及ready集合等。
//
//            while (true){
//                int readyChannel=selector.select();//检测是否有事件已经就绪，若无则返回-1，只检测上次调用select到这次调用select方法之间就绪的事件数
//                if (readyChannel==-1){
//                    continue;
//                }
//                Set<SelectionKey> selectionKeySet=selector.selectedKeys();//selectKeys方法返回就绪通道的集合
//                for (SelectionKey selectionKey:selectionKeySet){//检测四种事件是否就绪
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
