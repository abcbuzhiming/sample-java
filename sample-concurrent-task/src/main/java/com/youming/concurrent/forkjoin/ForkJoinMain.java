package com.youming.concurrent.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 出自：并行化-你的高并发大杀器 https://mp.weixin.qq.com/s/LN0ms-1ABLSEN629zGs8Ng
 * JDK1.7中提供了ForkJoinTask和ForkJoinPool
 * ForkJoinPool中每个线程都有自己的工作队列，并且采用Work-Steal算法防止线程饥饿。
 * Worker线程用LIFO的方法取出任务，但是会用FIFO的方法去偷取别人队列的任务，这样就减少了锁的冲突
 */
public class ForkJoinMain {
		
	public static void main(String[] args) {

		ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1);		//建立ForkJoinPool线程池，线程数CPU数-1
		System.out.println(forkJoinPool.invoke(new OrderTask()));		//打印返回的线程结果

	}
	
	
}
