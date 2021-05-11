package com.youming.concurrent.executor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 利用线程池来执行任务 Future：如何用多线程实现最优的“烧水泡茶”程序
 * https://time.geekbang.org/column/article/91292
 */
public class SampleFutureTask {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// 创建任务 T2 的 FutureTask
		FutureTask<String> ft2 = new FutureTask<>(new T2Task());
		// 创建任务 T1 的 FutureTask
		FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));
		// 线程 T1 执行任务 ft1
		Thread T1 = new Thread(ft1);
		T1.start();
		// 线程 T2 执行任务 ft2
		Thread T2 = new Thread(ft2);
		T2.start();
		// 等待线程 T1 执行结果
		System.out.println(ft1.get());

	}
}
