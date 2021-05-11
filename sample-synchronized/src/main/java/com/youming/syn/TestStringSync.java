package com.youming.syn;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Java中使用String作同步锁
 * https://cloud.tencent.com/developer/article/1352431
 * 利用String类型的字符常量池特性，以用户为身份区分进行代码块锁定，保证每次请求只有单线程
 * */
public class TestStringSync {

	private static Integer CNT = 0;
	
	public static void main(String[] args) {
		final String lock = new String("0-1");
        run(lock);
	}
	
	private static void run(String lock) {
        final Integer threadNum = 10;
        final CyclicBarrier cb = new CyclicBarrier(threadNum, new Runnable() {
            
            public void run() {
                System.out.println("threadNum : " + threadNum);
            }
        });
        
        for(int i = 0; i< threadNum; i++) {
            //String tmpLock = new String(lock);		//new出来的对象和之前string不是同一个，会引发线程不同步
        	int userType = 0;
        	int userId = 1;
        	String tmpLock = userType + "-" + userId;		//临时拼凑的string也会引发地址转变
        	//String tmpLock = lock;
            new TestThread(cb, tmpLock.intern()).start();		//唯一的解决办法是用intern求出字面量
        }
    }

    static class TestThread extends Thread {
        private CyclicBarrier cbLock;
        private String lock;
        
        public TestThread(CyclicBarrier cbLock, String lock) {
            this.cbLock = cbLock;
            this.lock = lock;
        }
        public void run() {
            try {
                cbLock.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            synchronized(lock) { //这里直接使用String对象本身作为锁
                CNT = CNT+1;
                System.out.println("Value:" + CNT);
            }           
        }
    }

}
