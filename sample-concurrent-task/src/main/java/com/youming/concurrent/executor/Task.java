package com.youming.concurrent.executor;

public class Task implements Runnable {

	private Result result;

	public Task(Result result) {
		this.result = result;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// 可以操作 result
	    String name = result.getName();
	    
	    System.out.println("线程任务开始执行");
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("线程任务结束执行");
	    result.setName(name);
	}

}
