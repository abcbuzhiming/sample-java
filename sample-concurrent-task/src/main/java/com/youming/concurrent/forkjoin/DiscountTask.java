package com.youming.concurrent.forkjoin;

import java.util.concurrent.RecursiveTask;

public class DiscountTask extends RecursiveTask<String> {

	@Override
	protected String compute() {
		// TODO Auto-generated method stub
		System.out.println("执行"+this.getClass().getSimpleName()+"线程名字为:"+Thread.currentThread().getName());
		return "DiscountTask finish work";
	}

}
