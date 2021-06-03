package com.hx.zbhuang.netty.dubboCall;

/**
 * 远程服务接口
 */
public interface HelloService {
    String hello(String mes);
    String say(String msg);
}
