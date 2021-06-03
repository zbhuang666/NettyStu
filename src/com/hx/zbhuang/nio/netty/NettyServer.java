package com.hx.zbhuang.nio.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) {
        //创建BoosGroup和WorkerGroup
        //bossGroup只处理请求连接真正的客户端业务处理由WorkerGroup完成
        //两个都是无限循环
        //BoosGroup和WorkerGroup含有NioEventLoop个数为CPU数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务启动对象，配置启动参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup) //设置连个线程组
                    .channel(NioServerSocketChannel.class) //使用NioServerSocketChannel作为服务器通道
                    .option(ChannelOption.SO_BACKLOG,128) //设置线程队列得到的连接数
                    .childOption(ChannelOption.SO_KEEPALIVE,true) //设置保持连接活跃状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("...server is ready...");

            //绑定一个端口并且同步，生成一个ChannelFuture
            ChannelFuture cf = serverBootstrap.bind(6668).sync();
            //对关闭的管道进行监听
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
