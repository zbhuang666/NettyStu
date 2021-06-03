package com.hx.zbhuang.nio;

public class DouFuThread {
    //装载库，保证JVM在启动的时候就会装载
    static {
        System.loadLibrary("DouFuThreadNative");
    }

    public static void main(String[] args) {
        DouFuThread douFuThread = new DouFuThread();
        douFuThread.start0();
    }

    public void run(){
        System.out.println("i am new Thead");
    }
    private native void start0();
}
