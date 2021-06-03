package com.hx.zbhuang.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress("127.0.0.1",9999);
        if(!socketChannel.connect(address)){
            while(!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间客户端不能阻塞，可以进行其他工作。。");
            }
        }
        //如果链接成功
        String str = "hello,尚硅谷~";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(byteBuffer);
        System.in.read();
    }
}
