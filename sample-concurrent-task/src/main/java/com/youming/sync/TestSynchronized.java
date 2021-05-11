package com.youming.sync;

/**
 * 测试synchronized关键字
 * */
public class TestSynchronized {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyThread syncThread1 = new MyThread(String.valueOf(1));
		MyThread syncThread2 = new MyThread(String.valueOf(1));
		//注意此刻是一个同一个实例对象
		Thread thread1 = new Thread(syncThread1, "SyncThread1");
		Thread thread2 = new Thread(syncThread2, "SyncThread2");
		thread1.start();
		thread2.start();

	}

}
