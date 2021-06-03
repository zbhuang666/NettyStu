package com.hx.zbhuang.netty.stickingAndUnpacking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.nio.charset.Charset;

/**
 * 客户端handler
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    /**
     * 服务端返回数据
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer,Charset.forName("utf-8"));
        System.out.println("客户端接受信息="+message);
        System.out.println("客户端接收消息数量="+(++this.count));
    }

    /**
     * 客户端启动发送十个包给客户端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       for(int i=0;i<10;i++) {
           ByteBuf buffer = Unpooled.copiedBuffer("数据包"+i+"==",Charset.forName("utf-8"));
           ctx.writeAndFlush(buffer);
       }
    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
