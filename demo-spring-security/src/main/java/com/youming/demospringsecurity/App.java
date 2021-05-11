package com.youming.demospringsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.youming.demospringsecurity.config.ApplicationConfig;


/**
 * 使用spring security的安全系统demo，用于对比和shiro不一样的地方
 * spring security需要依赖spring，但是并不一定非要用在web上，是可以离开web而存在的
 * 
 * */
public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println( "Hello demo-spring-security!" );
		ApplicationContext applicationContext = SpringApplication.run(ApplicationConfig.class, args); 
	}
}
