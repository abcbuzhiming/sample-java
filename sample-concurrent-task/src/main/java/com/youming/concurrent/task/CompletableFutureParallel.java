package com.youming.concurrent.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 出自：并行化-你的高并发大杀器 https://mp.weixin.qq.com/s/LN0ms-1ABLSEN629zGs8Ng
 * CompletableFuture，Jdk1.8加入
 * 优点，代码不再耦合
 * 缺点，仍然依赖自己定义的线程池，如果线程池是阻塞队列必然发生竞争
 * 
 */
public class CompletableFutureParallel {
	private static final int CORE_POOL_SIZE = 4;
	private static final int MAX_POOL_SIZE = 12;
	private static final long KEEP_ALIVE_TIME = 5L;
	private final static int QUEUE_SIZE = 1600;
	protected final static ExecutorService THREAD_POOL = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
			KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(QUEUE_SIZE));

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		//OrderInfoorderInfo = new OrderInfo(); // CompletableFuture 的List
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		futures.add(CompletableFuture.runAsync(() -> {
			System.out.println("当前任务Customer,线程名字为:" + Thread.currentThread().getName());
			//orderInfo.setCustomerInfo(new CustomerInfo());
		}, THREAD_POOL));
		futures.add(CompletableFuture.runAsync(() -> {
			System.out.println("当前任务Discount,线程名字为:" + Thread.currentThread().getName());
			//orderInfo.setDiscountInfo(new DiscountInfo());
		}, THREAD_POOL));
		futures.add(CompletableFuture.runAsync(() -> {
			System.out.println("当前任务Food,线程名字为:" + Thread.currentThread().getName());
			//orderInfo.setFoodListInfo(new FoodListInfo());
		}, THREAD_POOL));
		futures.add(CompletableFuture.runAsync(() -> {
			System.out.println("当前任务Other,线程名字为:" + Thread.currentThread().getName());
			//orderInfo.setOtherInfo(new OtherInfo());
		}, THREAD_POOL));
		
		CompletableFuture<Void> allDoneFuture = CompletableFuture
				.allOf(futures.toArray(new CompletableFuture[futures.size()]));		//等待所有任务完成
		allDoneFuture.get(10, TimeUnit.SECONDS);
		System.out.println("执行完毕");
		THREAD_POOL.shutdown();		//关闭线程池，否则程序不会结束
	}
}
