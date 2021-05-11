package com.youming.spring.boot.param.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.spring.boot.param.validation.config.SpringApplicationConfig;

/**
 * spring webmvc的参数使用hibernate validator校验的演示
 * 参考：
 * https://www.cnblogs.com/mr-yang-localhost/p/7812038.html
 * */
public class Application {

	public static void main(String[] args) {
		System.out.println("hello youming-sample-spring-boot-param-validation");
		SpringApplication.run(SpringApplicationConfig.class, args);
	}
}
