package com.hx.zbhuang.netty.dubboCall;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.lang.reflect.Method;

public class NettyServerHandler extends SimpleChannelInboundHandler {
    /**
     * 读取客户端需要调用的方法进行处理返回结果集
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg="+msg);
        String methodName = msg.toString().split("#")[1];
        Method[] methods = HelloService.class.getMethods();
        for (Method method:methods) {
            if(method.getName().equals(methodName)) {
                Object obj = method.invoke(new HelloServiceImpl(),msg.toString().substring(msg.toString().lastIndexOf("#")+1));
                ctx.writeAndFlush(obj.toString());
            }
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
        ctx.close();
    }
}
