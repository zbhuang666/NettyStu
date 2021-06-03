package com.hx.zbhuang.netty.dubboCall;

public class ClientBootstrap {
    // 暴露的接口名
    public static final String interfaceName = "HelloService";

    public static void main(String[] args) throws InterruptedException {
        NettyClient customer = new NettyClient();
        // 调用远程服务接口
        HelloService service = (HelloService)customer.getBean(HelloService.class, interfaceName);
        String msg = service.say("来打王者");
        System.out.println("调用结果"+ msg);
    }
}
