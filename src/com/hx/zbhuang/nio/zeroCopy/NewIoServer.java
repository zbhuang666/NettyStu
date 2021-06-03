package com.hx.zbhuang.nio.zeroCopy;

import com.sun.deploy.security.SelectableSecurityManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIoServer {
    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(9995));
            ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
            while (true){
                SocketChannel socketChannel = serverSocketChannel.accept();
                int readCount = -1;
                while(-1!=readCount){
                    socketChannel.read(byteBuffer);
                }
                byteBuffer.rewind();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
