package com.youming.sample.disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class LongEventMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 创建线程工厂
		ThreadFactory threadFactory = new ThreadFactory() {
			int i = 0;

			@Override
			public Thread newThread(Runnable runnable) {
				// TODO Auto-generated method stub
				return new Thread(runnable, String.valueOf(i++));
			}
		};
		// 创建工厂
		LongEventFactory factory = new LongEventFactory();
		// 创建bufferSize ,也就是RingBuffer大小，必须是2的N次方
		int ringBufferSize = 1024 * 1024; //

		/**
		 * 通过线程阻塞的方式，等待生产者唤醒，被唤醒后，再循环检查依赖的sequence是否已经消费
		 * BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
		 * */ 
		WaitStrategy blockingWaitStrategy = new BlockingWaitStrategy();
		/**
		 * BusySpinWaitStrategy：线程一直自旋等待，可能比较耗cpu
		 * SleepingWaitStrategy的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景
		 * */ 
		WaitStrategy sleepingWaitStrategy = new SleepingWaitStrategy();
		/**
		 * BusySpinWaitStrategy：尝试100次，然后Thread.yield()让出cpu
		 * YieldingWaitStrategy的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性
		 * */ 
		WaitStrategy yieldingWaitStrategy = new YieldingWaitStrategy();

		// 创建disruptor,单生产者模型
		Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, ringBufferSize, threadFactory,
				ProducerType.SINGLE, yieldingWaitStrategy);
		// 连接消费事件方法
		disruptor.handleEventsWith(new LongEventHandler());

		// 启动
		disruptor.start();

		// Disruptor 的事件发布过程是一个两阶段提交的过程：
		// 发布事件
		//RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();		//通过这里，传递了RingBuffer环

		for(long l = 0; l < 100; l++) {
			//发布消息
			disruptor.publishEvent((longEvent,sequence)->{
				//System.out.println("之前的数据：" + longEvent.getValue() + ";当前的sequence:" + sequence);
				longEvent.setValue(sequence);
			});
		}
		
		disruptor.shutdown();// 关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
		
	}

}
