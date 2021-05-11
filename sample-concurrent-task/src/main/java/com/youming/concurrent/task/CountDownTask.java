package com.youming.concurrent.task;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



/**
 * 出自：并行化-你的高并发大杀器 https://mp.weixin.qq.com/s/LN0ms-1ABLSEN629zGs8Ng
 * CountDownLatch/Phaser
 * CountDownLatch和Phaser是JDK提供的同步工具类Phaser是1.7版本之后提供的工具类而CountDownLatch是1.5版本之后提供的工具类，
 * CountDownLatch，可以将其看成是一个计数器，await()方法可以阻塞至超时或者计数器减至0，其他线程当完成自己目标的时候可以减少1，
 * 利用这个机制我们可以将其用来做并发
 * 缺陷，必须耦合CountDownLatch的代码
 */
public class CountDownTask {

	private static final int CORE_POOL_SIZE = 4;

	private static final int MAX_POOL_SIZE = 12;

	private static final long KEEP_ALIVE_TIME = 5L;

	private final static int QUEUE_SIZE = 1600;

	protected final static ExecutorService THREAD_POOL = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
			KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(QUEUE_SIZE));

	public static void main(String[] args) throws InterruptedException {

		// 新建一个为5的计数器
		CountDownLatch countDownLatch = new CountDownLatch(5);

		THREAD_POOL.execute(() -> {
			System.out.println("当前任务Customer,线程名字为:" + Thread.currentThread().getName());
			//orderInfo.setCustomerInfo(new CustomerInfo());
			countDownLatch.countDown();
		});
		THREAD_POOL.execute(() -> {
			System.out.println("当前任务Discount,线程名字为:" + Thread.currentThread().getName());
			//orderInfo.setDiscountInfo(new DiscountInfo());
			countDownLatch.countDown();
		});

		THREAD_POOL.execute(() -> {
			System.out.println("当前任务Food,线程名字为:" + Thread.currentThread().getName());
			//orderInfo.setFoodListInfo(new FoodListInfo());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			countDownLatch.countDown();
		});

		THREAD_POOL.execute(() -> {
			System.out.println("当前任务Tenant,线程名字为:" + Thread.currentThread().getName());
			//orderInfo.setTenantInfo(new TenantInfo());
			countDownLatch.countDown();

		});
		THREAD_POOL.execute(() -> {
			System.out.println("当前任务OtherInfo,线程名字为:" + Thread.currentThread().getName());
			//orderInfo.setOtherInfo(new OtherInfo());
			
			countDownLatch.countDown();
		});
		
		
		countDownLatch.await(5,	TimeUnit.SECONDS);

		System.out.println("主线程：" +	Thread.currentThread().getName());
		
		THREAD_POOL.shutdown();		//关闭线程池，否则程序不会结束

	}

}
