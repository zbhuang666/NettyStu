package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NioServer {
    Selector selector;
    ServerSocketChannel serverSocketChannel;

    public NioServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(8888));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                int count = selector.select();
                if(count > 0) {
                    Iterator<SelectionKey> selectionKeys = selector.keys().iterator();
                    while (selectionKeys.hasNext()) {
                        SelectionKey selectionKey = selectionKeys.next();
                        if(selectionKey.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector,SelectionKey.OP_READ);
                        }
                        if(selectionKey.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(2014);
                            int markCount = socketChannel.read(byteBuffer);
                            if(markCount > 0) {
                                for (SelectionKey key: selector.keys()) {
                                    Channel channel = key.channel();
                                    if(channel instanceof SocketChannel && channel != socketChannel) {
                                        SocketChannel otherChannel = (SocketChannel) channel;
                                        otherChannel.write(byteBuffer);
                                    }
                                }
                            }

                        }
                    }
                    selectionKeys.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NioServer nioServer = new NioServer();
        nioServer.listen();
    }
}
