package com.hx.zbhuang.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioBufferChannelInput {
    public static void main(String[] args) throws IOException {
        //获取需要读取的文件
        File file = new File("D:\\study\\document\\rabbitmq\\代码\\NettyStu\\text.txt");
        // 获取文件输入流
        FileInputStream inputStream = new FileInputStream(file);
        //获取通道
        FileChannel channel = inputStream.getChannel();
        //创建存储缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        // 将通道数据放入读到缓冲区
        channel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        inputStream.close();
    }
}
