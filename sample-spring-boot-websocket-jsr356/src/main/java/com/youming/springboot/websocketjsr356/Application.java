package com.youming.springboot.websocketjsr356;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.springboot.websocketjsr356.config.SpringApplicationConfig;



public class Application {

	public static void main(String[] args) {
		System.out.println("hello sample-spring-boot-websocket-jsr356");
		SpringApplication.run(SpringApplicationConfig.class, args);
	}
}
