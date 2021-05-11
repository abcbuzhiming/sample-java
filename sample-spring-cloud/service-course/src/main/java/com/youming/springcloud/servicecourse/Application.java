package com.youming.springcloud.servicecourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.springcloud.servicecourse.config.SpringApplicationConfig;
import com.youming.springcloud.servicecourse.utils.SpringUtils;


public class Application {

	public static void main(String[] args) {
		System.out.println("hello sample-spring-cloud-service-course");
		SpringApplication.run(SpringApplicationConfig.class, args);
	}
}
