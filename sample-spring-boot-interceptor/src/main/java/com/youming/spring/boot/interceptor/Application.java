package com.youming.spring.boot.interceptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.spring.boot.interceptor.config.InContainerConfig;

/**
 * spring webmvc的url拦截器演示
 * 参考：
 * https://o7planning.org/en/11689/spring-boot-interceptors-tutorial
 * https://docs.spring.io/spring-boot/docs/2.0.7.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-auto-configuration
 * */
public class Application {

	public static void main(String[] args) {
		System.out.println("hello youming-sample-spring-boot-interceptor");
		SpringApplication.run(InContainerConfig.class, args);
	}
}
