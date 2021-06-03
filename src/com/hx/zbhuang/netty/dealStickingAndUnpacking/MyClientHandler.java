package com.hx.zbhuang.netty.dealStickingAndUnpacking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.nio.charset.Charset;

/**
 * 客户端handler
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    /**
     * 客户端启动发送封装的协议包给服务端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      for (int i=0;i<5;i++){
          String msg = "一起打王者";
          byte[] content = msg.getBytes(Charset.forName("utf-8"));
          int length = msg.getBytes(Charset.forName("utf-8")).length;
          //创建协议包对象
          MessageProtocol messageProtocol =new MessageProtocol();
          messageProtocol.setLen(length);
          messageProtocol.setContent(content);
          ctx.writeAndFlush(messageProtocol);
      }
    }

    /**
     * 读取服务端反馈信息
     * @param channelHandlerContext
     * @param messageProtocol
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        int len = messageProtocol.getLen();
        byte[] content = messageProtocol.getContent();
        System.out.println("=========================");
        System.out.println("客户端接收消息入如下");
        System.out.println("长度="+len);
        System.out.println("内容="+new String(content,Charset.forName("utf-8")));
        System.out.println("客户端接收消息数量="+(++this.count));
    }

    /**
     * 异常捕获
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
