package com.luban1.demo10;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue
 * 容器里每一个元素都设置了一个时间，时间到了才能从中提取元素
 */
public class Demo {

	private static BlockingQueue<MyTask> tasks = new DelayQueue<>();

	static class MyTask implements Delayed{

		long runningTime;
		String name;
		
		public MyTask(long rt,String name) {
			this.runningTime = rt;
			this.name = name;
		}

		@Override
		public int compareTo(Delayed o) {
			if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MICROSECONDS)) {
				return -1;
			}else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
				return 1;
			}else {
				return 0;
			}
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}
		
		@Override
		public String toString() {
			return "" + runningTime + "  "+name;
		}
		
		public static void main(String[] args) throws InterruptedException {
			long now = System.currentTimeMillis();
			MyTask t1 = new MyTask(now+1000,"t1");
			MyTask t2 = new MyTask(now+2000,"t2");
			MyTask t3 = new MyTask(now+1500,"t3");
			MyTask t4 = new MyTask(now+2500,"t4");
			MyTask t5 = new MyTask(now+500,"t5");
			
			tasks.put(t1);
			tasks.put(t2);
			tasks.put(t3);
			tasks.put(t4);
			tasks.put(t5);
			
			System.out.println(tasks);
			
			for (int i = 0; i < 5; i++) {
				System.out.println(tasks.take());
			}
		}

	}

}
