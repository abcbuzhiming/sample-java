package com.youming.spring.boot.scheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.spring.boot.scheduling.config.InContainerConfig;

/**
 * 使用Spring TaskScheduler进行异步任务和计划调度
 * 参考：
 * https://spring.io/guides/gs/async-method/
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling-task-scheduler
 * https://blog.csdn.net/qq_26525215/article/details/66974880
 * */
public class Application {

	public static void main(String[] args) {
		System.out.println("hello youming-sample-spring-boot-scheduling");
		SpringApplication.run(InContainerConfig.class, args);
	}
}
