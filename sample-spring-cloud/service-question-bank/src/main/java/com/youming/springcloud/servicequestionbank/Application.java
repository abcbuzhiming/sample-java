package com.youming.springcloud.servicequestionbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.springcloud.servicequestionbank.config.SpringApplicationConfig;
import com.youming.springcloud.servicequestionbank.utils.SpringUtils;

/**
 * 语法参考来源：
 * http://freemarker.foofun.cn/
 * https://segmentfault.com/a/1190000011768799
 * */
public class Application {

	public static void main(String[] args) {
		System.out.println("hello sample-spring-cloud-service-question-bank");
		SpringApplication.run(SpringApplicationConfig.class, args);
	}
}
