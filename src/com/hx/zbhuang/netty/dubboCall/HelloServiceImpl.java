package com.hx.zbhuang.netty.dubboCall;

/**
 * 远程服务实现类
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        if(msg!=null) {
            return "hello 豆腐蛋，i accept you msg:["+msg+"]";
        } else {
            return "hello 豆腐蛋，i accept you msg";
        }
    }

    @Override
    public String say(String msg) {
        return "hello 豆腐蛋" + "==msg:"+msg;
    }
}
