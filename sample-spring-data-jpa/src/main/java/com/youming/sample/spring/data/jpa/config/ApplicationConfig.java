package com.youming.sample.spring.data.jpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.youming.sample.spring.data.jpa.utils.SpringUtils;



@Configuration
@SpringBootApplication
@ComponentScan(basePackages = { "com.youming.sample.spring.data.jpa"},excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = Configuration.class) }) // 自动扫描包范围,相当于xml配置:context:component-scan
@EnableJpaRepositories(basePackages="com.youming.sample.spring.data.jpa.repository")		//jpa Repository的包扫描路径(必须)
@EntityScan(basePackages="com.youming.sample.spring.data.jpa.dto")		//实体映射类扫描路径(必须)
@EnableAspectJAutoProxy(proxyTargetClass = true) // 指定使用CGLIB代理，aop，相当于xml配置:<aop:aspectj-autoproxy
// proxy-target-class="true"/>
@EnableTransactionManagement // 事务
@PropertySource({ "classpath:application.properties",})
public class ApplicationConfig {

	@Autowired
	private Environment environment;

	// 专门用于注入保留ApplicationContext的工具类
	@Bean(name = "springUtils")
	public SpringUtils springUtils() {
		SpringUtils springUtils = new SpringUtils();
		return springUtils;
	}
}
