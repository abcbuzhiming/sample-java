package com.youming.spring.boot.scheduling.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 计划任务演示 
 * 计划任务不需要被显式调用，只要被Spring扫到就会触发
 */
@Component
public class TaskLogic {

	private static final Logger logger = LoggerFactory.getLogger(TaskLogic.class);

	/**
	 * @throws Exception 
	 * @Scheduled 可以作为一个触发源添加到一个方法中 以一个固定延迟时间5秒钟调用一次执行
	 *            这个周期是以上一个调用任务的##完成时间##为基准，在上一个任务完成之后，5s后再次执行
	 */
	@Scheduled(fixedDelay = 5000)
	public void demo1(){
		logger.info("定时任务demo1开始......");

		long begin = System.currentTimeMillis();
		// 执行你需要操作的定时任务
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		logger.info("定时任务demo1结束，共耗时：[" + (end - begin) + "]毫秒");
	}

	/**
	 * 以一个固定延迟时间5秒钟调用一次执行 这个周期是以上一个任务##开始时间##为基准，从上一任务开始执行后5s再次调用：
	 */
	@Scheduled(fixedRate = 5000)
	public void demo2() {
		logger.info("定时任务demo2开始.");
		long begin = System.currentTimeMillis();
		// 执行你需要操作的定时任务
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		logger.info("定时任务demo2结束，共耗时：[" + (end - begin) + "]毫秒");
	}

	/**
	 * 如果你需要在特定的时间执行，就需要用到cron 了 按顺序依次为 1 秒（0~59） 2 分钟（0~59） 3 小时（0~23） 4 天（0~31） 5
	 * 月（0~11） 6 星期（1~7 1为SUN-依次为SUN，MON，TUE，WED，THU，FRI，SAT） 7.年份（1970－2099）
	 * 这里是在每天的13点30分执行一次 注意：从spring 4.x开始，cron不再支持"年"的单位设置
	 */
	@Scheduled(cron = "0 34 13 * * ?")
	public void demo3() {
		logger.info("定时任务demo3开始.");
		long begin = System.currentTimeMillis();
		// 执行你需要操作的定时任务
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		logger.info("定时任务demo3结束，共耗时：[" + (end - begin) + "]毫秒");
	}

}
