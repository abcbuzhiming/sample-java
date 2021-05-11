package com.youming.demospringwebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.youming.demospringwebsocket.config.ApplicationConfig;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println( "Hello demo-spring-websocket!" );
		ApplicationContext applicationContext = SpringApplication.run(ApplicationConfig.class, args); 
	}

}
