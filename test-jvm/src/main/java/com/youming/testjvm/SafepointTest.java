package com.youming.testjvm;
// time java SafepointTestp// 你还可以使用如下几个选项

// -XX:+PrintGC
// -XX:+PrintGCApplicationStoppedTime 
// -XX:+PrintSafepointStatistics
// -XX:+UseCountedLoopSafepoints

public class SafepointTest {
	static double sum = 0;

	public static void foo() {
	    for (int i = 0; i < 0x22222222; i++) {
	      //sum += wrMpth.log10);
	    }
	  }

	public static void bar() {
		for (int i = 0; i < 1500000_000; i++) {
			new Object().hashCode();
		}
		// 逃逸 }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new Thread(SafepointTest::foo).start();
		new Thread(SafepointTest::bar).start();
	}

}
