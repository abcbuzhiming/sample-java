package com.youming.sync;

/**
 * 参考:
 * 深入解析String#intern - https://tech.meituan.com/2014/03/06/in-depth-understanding-string-intern.html
 * */
public class MyThread implements Runnable {
	private int count;
	private final String key;
	
	public MyThread(String key) {
        this.key = key;
		count = 0;
       
    }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//如果不使用intern()获取字面量是无法锁定的,
		//String的String Pool是一个固定大小的Hashtable(线程安全)，默认值大小长度是1009，如果放进String Pool的String非常多，就会造成Hash冲突严重，从而导致链表会很长，
		//而链表长了后直接会造成的影响就是当调用String.intern时性能会大幅下降（因为要一个一个找）。
		/*因此使用String.intern()的方式实现同步锁并不是一个良好的方案*/
		synchronized(key.intern()) {		
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

	}

}
