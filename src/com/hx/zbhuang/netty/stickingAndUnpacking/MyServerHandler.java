package com.hx.zbhuang.netty.stickingAndUnpacking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.nio.charset.Charset;

/**
 * 服务端handler
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    /**
     * 获取客户端数据包内容反馈接收次数
     * @param ctx
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        byte[] buffer = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(buffer);
        String message = new String(buffer,Charset.forName("utf-8"));
        System.out.println("服务器接收到的数据"+message);
        System.out.println("服务器接收消息数量"+(++this.count));
        ByteBuf responseBuf = Unpooled.copiedBuffer("服务端说:我收到了"+this.count+"次数据包",Charset.forName("utf-8"));
        ctx.writeAndFlush(responseBuf);
    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
