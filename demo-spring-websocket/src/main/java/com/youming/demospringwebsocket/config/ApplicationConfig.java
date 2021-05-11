package com.youming.demospringwebsocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * spring boot app 应用的主配置文件
 */
@Configuration
@EnableAutoConfiguration // spring boot将根据你添加的依赖自动进行配置
@ComponentScan(basePackages = { "com.youming.demospringwebsocket" }) // 自动扫描包范围,相当于xml配置:context:component-scan
@EnableAspectJAutoProxy(proxyTargetClass = true) // 指定使用CGLIB代理，aop，相当于xml配置:<aop:aspectj-autoproxy
													// proxy-target-class="true"/>
@EnableTransactionManagement // 事务
@PropertySource({ "classpath:application.properties" }) // 注入多个配置文件如果是相同的key，则最后一个起作用

@Import({ WebSocketConfig.class }) // 注入别的配置类
public class ApplicationConfig {

	@Autowired
	private Environment environment;
}
