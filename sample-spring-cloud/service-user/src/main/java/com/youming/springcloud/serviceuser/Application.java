package com.youming.springcloud.serviceuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.springcloud.serviceuser.config.SpringApplicationConfig;
import com.youming.springcloud.serviceuser.utils.SpringUtils;


public class Application {

	public static void main(String[] args) {
		System.out.println("hello sample-spring-cloud-service-user");
		SpringApplication.run(SpringApplicationConfig.class, args);
	}
}
