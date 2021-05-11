package com.youming.spring.boot.scheduling.config;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.ErrorHandler;

/**
 * 计划调度配置类
 * */
@Configuration
@EnableScheduling // 计划调度@Scheduled注解需要
public class SchedulingConfigurerImpl implements SchedulingConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(SchedulingConfigurerImpl.class);
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
		// TODO Auto-generated method stub
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(5);		//线程数，默认1
		threadPoolTaskScheduler.setThreadNamePrefix("taskScheduler");	//  线程名称前缀
		//错误处理器
		ErrorHandler errorHandler = new ErrorHandler() {
			@Override
			public void handleError(Throwable throwable) {
				// TODO Auto-generated method stub
				System.out.println("-------------》》》捕获线程异常信息");
				throwable.printStackTrace();
				logger.error("Exception message - " + throwable.getMessage());
	            
			}
		};
		threadPoolTaskScheduler.setErrorHandler(errorHandler);
		threadPoolTaskScheduler.initialize(); // 初始化
		scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
	}

}
