package com.hx.zbhuang.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT =6667;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(PORT);
            listenChannel.socket().bind(address);
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        while(true) {
            try {
                int count = selector.select();
                if(count>0){
                    Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                    while (selectionKeyIterator.hasNext()) {
                        SelectionKey selectionKey = selectionKeyIterator.next();
                        if(selectionKey.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress()+"上线了");
                        }
                        if(selectionKey.isReadable()) {
                            readData(selectionKey);
                        }
                        selectionKeyIterator.remove();
                    }
                } else {
                    System.out.println("等待...");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readData(SelectionKey selectionKey){
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = channel.read(byteBuffer);
            if(count>0){
                String msg = new String(byteBuffer.array());
                System.out.println("from 客户端"+msg);
                sendInfoToOtherClients(msg,channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress()+"离线了");
                selectionKey.cancel();
                channel.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }

        }
    }

    public void sendInfoToOtherClients(String msg,SocketChannel desChannel) throws IOException {
        System.out.println("服务器转发消息中");
        for (SelectionKey selectionKey:selector.keys()) {
            Channel curChannel = selectionKey.channel();
            if(curChannel instanceof SocketChannel && curChannel!=desChannel) {
                SocketChannel desSocketChannel = (SocketChannel) curChannel;
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                desSocketChannel.write(byteBuffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
