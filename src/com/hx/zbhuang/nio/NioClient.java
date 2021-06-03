package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class NioClient {
    Selector selector;
    SocketChannel socketChannel;
    String username = "";

    public NioClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",8888));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector,SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInfo(String msg) {
        msg = username + "说" + msg;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo(){
        try {
            int readChannels = selector.select();
            if(readChannels > 0) {
                Iterator<SelectionKey> selectionKeys = selector.keys().iterator();
                while (selectionKeys.hasNext()) {
                    SelectionKey selectionKey = selectionKeys.next();
                    if(selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
                        channel.read(byteBuffer);
                        String massage = new String(byteBuffer.array());
                        System.out.println(massage.trim());
                    }
                }
                selectionKeys.remove();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    NioClient client = new NioClient();
                    client.readInfo();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        NioClient client = new NioClient();
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.nextLine();
        client.sendInfo(msg);

    }
}
