package com.youming.spring.boot.secure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.spring.boot.secure.config.SpringApplicationConfig;

/**
 * spring boot + spring security
 * 
 * */
public class Application {

	public static void main(String[] args) {
		System.out.println("hello sample-spring-boot-security");
		SpringApplication.run(SpringApplicationConfig.class, args);
	}
}
