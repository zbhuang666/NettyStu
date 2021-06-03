package com.hx.zbhuang.nio;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test {
    public void loop() {
        while(true) {

        }
    }
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        System.out.println(NettyRuntime.availableProcessors());
        new test().loop();
        ExecutorService service1 = Executors.newCachedThreadPool();
        ExecutorService service2 = Executors.newFixedThreadPool(10);
        ExecutorService service3 = Executors.newSingleThreadExecutor();
        ExecutorService service4 = Executors.newScheduledThreadPool(10);
    }
}
