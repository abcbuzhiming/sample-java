package com.youming.concurrent.forkjoin;

import java.awt.event.MouseWheelEvent;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class OrderTask extends RecursiveTask<String> {

	@Override
	protected String compute() {
		// TODO Auto-generated method stub
		System.out.println("执行"+this.getClass().getSimpleName()+"线程名字为:"+Thread.currentThread().getName());
		CustomerTask customerTask  = new CustomerTask();
		TenantTask tenantTask = new TenantTask();
		DiscountTask discountTask = new DiscountTask();
		FoodTask foodTask = new FoodTask();
		OtherTask otherTask = new OtherTask();
		
		ForkJoinTask.invokeAll(customerTask,tenantTask,discountTask,foodTask,otherTask);		//提交任务
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(customerTask.join() + System.getProperty("line.separator"));
		stringBuilder.append(tenantTask.join() + System.getProperty("line.separator"));
		stringBuilder.append(discountTask.join() + System.getProperty("line.separator"));
		stringBuilder.append(foodTask.join() + System.getProperty("line.separator"));
		stringBuilder.append(otherTask.join()+ System.getProperty("line.separator"));
		
		return stringBuilder.toString();
	}

}
