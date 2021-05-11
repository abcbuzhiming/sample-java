package com.youming.spring.boot.druid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.spring.boot.druid.config.SpringApplicationConfig;

/**
 * 演示druid连接池的状态监视效果.
 * https://github.com/alibaba/druid
 * https://github.com/alibaba/druid/wiki/常见问题
 * 启动后访问地址：http://localhost:8080/druid/login.html,用户名密码在DruidConfig中由ServletRegistrationBeanStatViewServlet设置
 */
public class Application {

	public static void main(String[] args) {
		System.out.println("hello youming-sample-spring-boot-druid");
		SpringApplication.run(SpringApplicationConfig.class, args);
	}
}
