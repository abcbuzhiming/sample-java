package com.youming.spring.boot.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.spring.boot.thymeleaf.config.InContainerConfig;

/**
 * 语法参考来源：
 * https://blog.csdn.net/zrk1000/article/details/72667478
 * thymeleaf的问题在于标签属性式的变量绑定写法虽然契合H5的风格，但是实践成本较高，
 * 且在js这样无法使用这种写法的时候保留的内联变量写法[[...]] [(...)]又和很多js模板发生冲突，非常不友好
 * 我个人不建议使用thymeleaf作为生产环境下的模板引擎，目前来说freemarker还是最优选择
 * */
public class Application {

	public static void main(String[] args) {
		System.out.println("hello youming-sample-spring-boot-thymeleaf");
		SpringApplication.run(InContainerConfig.class, args);
	}
}
