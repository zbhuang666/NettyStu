package com.hx.zbhuang.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioBufferChannelOutput {
    public static void main(String[] args) throws IOException {
        String str = "nio buffer file";
        //创建一个输出流
        FileOutputStream outputStream = new FileOutputStream("D:\\study\\document\\rabbitmq\\代码\\NettyStu\\text.txt");
        //创建一个file通道
        FileChannel channel = outputStream.getChannel();
        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        //将缓冲数据写到通道
        channel.write(byteBuffer);
        outputStream.close();
    }
}
