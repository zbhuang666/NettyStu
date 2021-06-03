package com.hx.zbhuang.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 个人理解
 *  服务端会将暴露的插槽注册到selector置为accept状态
 *  监听selector的selectionKey
 *  如果有客户端连接，客户端连接插槽后返回一个通道，将通道注册到selector置为read状态
 *  如果有客户端发生读操作，通过selectionKey来获取通道和缓冲池进行读取
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //创建socket通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建selector
        Selector selector = Selector.open();
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        //关闭阻塞
        serverSocketChannel.configureBlocking(false);
        //将chanel注册到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true) {
            if(selector.select(1000)==0) {
                System.out.println("服务等待了1秒，无连接");
                continue;
            }
            //获取所有的selectionkey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //获取迭代器
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            //遍历是否有通道链接
            while (iterator.hasNext()) {
                //获取链接的selectionKey
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isAcceptable()){
                    //为连接成功的可获段生成一个通道
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //将通道注册到selector
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(byteBuffer);
                    System.out.println("form 客户端"+new String(byteBuffer.array()));
                }
                iterator.remove();
            }
        }
    }
}
