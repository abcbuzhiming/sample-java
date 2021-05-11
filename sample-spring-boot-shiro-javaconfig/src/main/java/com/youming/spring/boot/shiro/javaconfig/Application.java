package com.youming.spring.boot.shiro.javaconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.spring.boot.shiro.javaconfig.config.SpringApplicationConfig;

/**
 * shiro以JavaConfig的方式引入
 * */

public class Application { 

	public static void main(String[] args) {
		System.out.println("hello youming-sample-spring-boot-shiro-javaconfig");
		SpringApplication.run(SpringApplicationConfig.class, args); 
	}
}
