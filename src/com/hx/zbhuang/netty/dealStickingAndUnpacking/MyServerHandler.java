package com.hx.zbhuang.netty.dealStickingAndUnpacking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * 服务端handler
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    /**
     * 服务端获取客户端数据包进行反馈
     * @param channelHandlerContext
     * @param messageProtocol
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        int len = messageProtocol.getLen();
        byte[] content = messageProtocol.getContent();
        System.out.println("======================");
        System.out.println("服务器接收消息如下");
        System.out.println("长度="+len);
        System.out.println("内容="+new String(content,Charset.forName("utf-8")));
        System.out.println("接收到包的数量="+(++this.count));
        String responseContent = "服务端说:我收到了"+this.count+"次数据包";
        int responseLen = responseContent.getBytes("utf-8").length;
        byte[] data = responseContent.getBytes("utf-8");
        MessageProtocol msg = new MessageProtocol();
        msg.setLen(responseLen);
        msg.setContent(data);
        channelHandlerContext.writeAndFlush(msg);
    }

    /**
     * 异常捕获
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
