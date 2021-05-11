package com.youming.concurrent.executor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 利用线程池来执行任务 Future：如何用多线程实现最优的“烧水泡茶”程序
 * https://time.geekbang.org/column/article/91292
 */
public class SampleFuture {

	public static void main(String[] args) {
		System.out.println("hello future");
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		// 创建 Result 对象 r
		Result result = new Result();
		result.setName("123");
		// 提交任务
		Future<Result> future = executor.submit(new Task(result), result);
		try {
			System.out.println("wait future");
			Result futureResult = future.get();		//阻塞在这里等待返回
			System.out.println("wait future end");
			System.out.println(futureResult.getName());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}

	}
}
