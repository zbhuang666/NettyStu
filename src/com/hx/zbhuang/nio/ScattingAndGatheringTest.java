package com.hx.zbhuang.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScattingAndGatheringTest {
    public static void main(String[] args) throws IOException {
        // 创建socket通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(7777));
        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0]=ByteBuffer.allocate(5);
        byteBuffers[1]=ByteBuffer.allocate(3);
        //等待客户端连接
        SocketChannel channel = serverSocketChannel.accept();
        int messageLength = 8;
        while(true) {
          int byteRead=0;
          while (byteRead<messageLength) {
              long l = channel.read(byteBuffers);
              byteRead+=1;
              System.out.println("byteRead="+byteRead);
              Arrays.asList(byteBuffers).stream().map(buffer -> "position=" + buffer.position()+",limit="+buffer.limit()).forEach(System.out::println);
          }
          System.out.println(Arrays.toString(byteBuffers));
          //将所有buffer进行flip
          Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());
          long byteWrite = 0;
          while(byteWrite<messageLength){
            Long l = channel.write(byteBuffers);
            byteWrite+=1;
          }
          System.out.println(Arrays.toString(byteBuffers));
          Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());
          System.out.println("byteRead="+byteRead+",byteWrite="+byteWrite+",messageLength="+messageLength);
        }
    }
}
