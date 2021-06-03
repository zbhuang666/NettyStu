package com.luban.demo1;

public class Demo2 {

    private int count = 10;

    public void test(){
        //synchronized(this)锁定的是当前类的实例,这里锁定的是Demo2类的实例
        synchronized (this){
            System.out.println("this");
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
    public void test1(){
        //synchronized(this)锁定的是当前类的实例,这里锁定的是Demo2类的实例
        synchronized (this.getClass()){
            System.out.println("this.getClass");
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        new Thread(demo2::test,"t1").start();
        new Thread(demo2::test1,"t1").start();
    }

}
