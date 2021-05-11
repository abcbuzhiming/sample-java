package com.youming.demobootehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.youming.demobootehcache.config.ApplicationConfig;

/**
 * spring中使用基于JSR-107标准的缓存注解的办法
 * 参考:https://spring.io/blog/2014/04/14/cache-abstraction-jcache-jsr-107-annotations-support
 * 配置:http://www.ehcache.org/blog/2016/05/18/ehcache3_jsr107_spring.html
 * 配置:https://blog.csdn.net/cml_blog/article/details/54767136
 * */

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println( "Hello demo-boot-ehcache3!" );
		ApplicationContext applicationContext = SpringApplication.run(ApplicationConfig.class, args); 
	}

}
