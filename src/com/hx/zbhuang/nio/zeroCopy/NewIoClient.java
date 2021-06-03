package com.hx.zbhuang.nio.zeroCopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIoClient {
    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1",9995));
            String fileName = "protoc-3.6.1-win32.zip";
            FileChannel fileChannel =new FileInputStream(fileName).getChannel();
            long start = System.currentTimeMillis();
            long transferCount = fileChannel.transferTo(0,fileChannel.size(),socketChannel);
            System.out.println("发送字节耗时:"+transferCount+",耗时:"+(System.currentTimeMillis()-start));
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
