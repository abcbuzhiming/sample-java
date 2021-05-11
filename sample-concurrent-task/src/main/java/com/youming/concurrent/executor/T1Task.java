package com.youming.concurrent.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


//T1Task 需要执行的任务：
//洗水壶、烧开水、泡茶
public class T1Task implements Callable<String> {

	FutureTask<String> ft2;

	// T1 任务需要 T2 任务的 FutureTask
	T1Task(FutureTask<String> ft2) {
		this.ft2 = ft2;
	}

	@Override
	public String call() throws Exception {
		System.out.println("T1: 洗水壶...");
		
		TimeUnit.SECONDS.sleep(1);

		System.out.println("T1: 烧开水...");
		TimeUnit.SECONDS.sleep(15);
		// 获取 T2 线程的茶叶
		String tf = ft2.get();
		System.out.println("T1: 拿到茶叶:" + tf);

		System.out.println("T1: 泡茶...");
		return " 上茶:" + tf;
	}

}
