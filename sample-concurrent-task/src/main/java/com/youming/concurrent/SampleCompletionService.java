package com.youming.concurrent;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CompletionService 的实现原理也是内部维护了一个阻塞队列，当任务执行结束就把任务的执行结果加入到阻塞队列中，
 * 不同的是 CompletionService 是把任务执行结果的 Future 对象加入到阻塞队列中，
 * */
public class SampleCompletionService {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		// 创建线程池
		ExecutorService executor = Executors.newFixedThreadPool(3);
		// 创建 CompletionService
		CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
		// 异步向电商 S1 询价
		completionService.submit(()->{
			System.out.println("S1询价开始");
			TimeUnit.SECONDS.sleep(2);
			System.out.println("S1询价结束");
			return 1;
		});
		// 异步向电商 S2 询价
		completionService.submit(()->{
			System.out.println("S2询价开始");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("S2询价结束");
			return 2;
		});
		// 异步向电商 S3 询价
		completionService.submit(()->{
			System.out.println("S3询价开始");
			TimeUnit.SECONDS.sleep(3);
			System.out.println("S3询价结束");
			return 3;
		});
		// 将询价结果异步保存到数据库
		for (int i=0; i<3; i++) {
		  Integer r = completionService.take().get();		//这里的策略是，谁先完成谁就出结果
		  System.out.println("询价结果:" + r);
		}
		
		executor.shutdown();
	}

}
