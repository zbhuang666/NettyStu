package com.hx.zbhuang.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioBufferChannelIO {
    public static void main(String[] args) throws IOException {
        File fileIn = new File("D:\\study\\document\\rabbitmq\\代码\\NettyStu\\text.txt");
        FileInputStream inputStream = new FileInputStream(fileIn);
        File fileOut = new File("D:\\study\\document\\rabbitmq\\代码\\NettyStu\\text.txt1");
        FileOutputStream outputStream = new FileOutputStream(fileOut);
        FileChannel channelIn = inputStream.getChannel();
        FileChannel channelOut = outputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while(true) {
            byteBuffer.clear();
            int readLine = channelIn.read(byteBuffer);
            if(readLine == -1) {
                break;
            }
            byteBuffer.flip();
            channelOut.write(byteBuffer);
        }
        inputStream.close();
        outputStream.close();
    }
}
