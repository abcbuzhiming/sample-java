package com.youming.spring.boot.interceptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.spring.boot.interceptor.config.SpringApplicationConfig;

//参考：
//https://www.cnblogs.com/OnlyCT/p/9563443.html
//Spring AOP学习笔记(1)-概念 https://www.cnblogs.com/manliu/p/5985721.html
/**
 * spring boot 基于方法的拦截器AOP演示,自定义注解实现，高度灵活
 * 
 */
public class Application {

	public static void main(String[] args) {
		System.out.println("hello youming-sample-spring-boot-interceptor-method");
		SpringApplication.run(SpringApplicationConfig.class, args);
	}
}
