package com.hx.zbhuang.nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * ChannelHandlerContext上下文，含有管道pipeline,通道channel，地址等
     * @param ctx
     * @param msg 客户端发来的信息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务读取的线程数"+Thread.currentThread().getName());
        System.out.println("server channelHandlerContext:" +ctx);
        System.out.println("channel与pipeline的关系");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();
        //将msg装换为ByteBuf 为netty封装
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端"+channel.remoteAddress()+"发送的信息是:"+buf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端O(∩_∩)O哈哈~喵",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
