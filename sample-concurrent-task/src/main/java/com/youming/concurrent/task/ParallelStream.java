package com.youming.concurrent.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

/**
 * 出自：并行化-你的高并发大杀器 https://mp.weixin.qq.com/s/LN0ms-1ABLSEN629zGs8Ng
 * 在jdk1.8中提供了并行流的API，当我们使用集合的时候能很好的进行并行处理，
 * 下面举了一个简单的例子从1加到100
 * 这里的parallelStream是并行操作的，所以必须用LongAdder这样的线程安全变量进行累加
 */
public class ParallelStream {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 1 ;i<=100;i++) {
			list.add(i);
		}
		LongAdder sum = new LongAdder();
		list.parallelStream().forEach(integer->{
			sum.add(integer);
		});
		System.out.println("最终结果" + sum);
	}

}
