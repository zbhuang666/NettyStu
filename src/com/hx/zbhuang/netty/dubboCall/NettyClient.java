package com.hx.zbhuang.netty.dubboCall;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {
    //线程池管理客户端请求
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private  static NettyClientHandler client;

    /**
     * 返回服务端接口方法调用结果集
     * @param serviceClass
     * @param interfaceName
     * @return
     */
    Object getBean(final Class<?> serviceClass, final String interfaceName){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class<?>[]{serviceClass},(proxy,method,args) -> {
            if (client ==null) {
                initClient();
            }
            client.setPara(interfaceName+"#"+method.getName()+"#"+args[0]);
            Object obj = executor.submit(client).get();
            return obj;
        });
    }

    /**
     * 客户端初始化
     */
    private static void initClient() {
        client = new NettyClientHandler();
        NioEventLoopGroup group =null;
        try {
            group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline=socketChannel.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            // 客户端拦水坝处理
                            pipeline.addLast(client);
                        }
                    });
            bootstrap.connect("127.0.0.1",7766).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
