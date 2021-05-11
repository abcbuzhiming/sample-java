package com.youming.spring.boot.shiro.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.spring.boot.shiro.start.config.InContainerConfig;

/**
 * https://blog.csdn.net/gnail_oug/article/details/80662553
 * https://github.com/YorkeCao/shiro-spring-boot-sample
 * 测试表明，shiro-spring-boot-starter存在缺陷
 * 如果引入spring-aspects，那么在service层上使用shiro注解启动时就会出错；如果在Controller层使用shiro注解，那么被注解的Controller里所有@RequestMapping都会失灵
 * */

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(InContainerConfig.class, args);
	}
}
