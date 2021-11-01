package com.hx.zbhuang.bio;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOClient {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //Connection reset 连接被重置
        for (int i=0;i<10;i++) {
            CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    send();
                }
            }, executorService);
        }
    }

    public static void send(){
        BufferedWriter bdw = null;
        try {
            //创建套接字的连接对象
            Socket socket = new Socket("127.0.0.1",6666);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            bdw = new BufferedWriter(osw);
            String text="您好\n\r";
            bdw.write(text, 0, text.length());
            bdw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bdw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
