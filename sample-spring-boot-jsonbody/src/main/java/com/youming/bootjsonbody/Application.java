package com.youming.bootjsonbody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.bootjsonbody.config.InContainerConfig;
import com.youming.bootjsonbody.utils.SpringUtils;

/**
 * 参考来源：
 * https://www.cnblogs.com/threadj/p/10535760.html  一般情况的json参数解析
 * https://www.cnblogs.com/qm-article/p/10199622.html  自定义json参数解析器
 * */
public class Application {

	public static void main(String[] args) {
		System.out.println("hello sample-spring-boot-jsonbody");
		SpringApplication.run(InContainerConfig.class, args);
	}
}
