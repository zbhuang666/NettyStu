package com.hx.zbhuang.nio.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

public class ODHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest) {
            System.out.println("pipeline hashcode"+ctx.pipeline().hashCode()+"TestHttpServerHandler hash="+this.hashCode());
            System.out.println("msg 类型="+msg.getClass());
            System.out.println("客户端地址"+ctx.channel().remoteAddress());
            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())) {
                System.out.println("不接收响应");
                return;
            }
            //回应给浏览器
            ByteBuf content = Unpooled.copiedBuffer("hello,我市服务器", CharsetUtil.UTF_8);
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,content);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,CharsetUtil.UTF_8);
            ctx.writeAndFlush(httpResponse);
        }
    }
}
