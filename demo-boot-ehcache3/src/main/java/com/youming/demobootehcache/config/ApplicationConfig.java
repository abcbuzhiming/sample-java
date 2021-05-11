package com.youming.demobootehcache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.youming.demobootehcache.utils.SpringUtils;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * spring boot app 应用的主配置文件
 */
@Configuration
@EnableAutoConfiguration // spring boot将根据你添加的依赖自动进行配置
@ComponentScan(basePackages = { "com.youming.demobootehcache" }, excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = Configuration.class) }) // 自动扫描包范围,相当于xml配置:context:component-scan
@EnableAspectJAutoProxy(proxyTargetClass = true) // 指定使用CGLIB代理，aop，相当于xml配置:<aop:aspectj-autoproxy
													// proxy-target-class="true"/>
@EnableTransactionManagement // 事务
@PropertySource({ "classpath:application.properties" }) // 注入多个配置文件如果是相同的key，则最后一个起作用
@EnableCaching		//使用缓存
// @Import({ MybatisConfig.class }) // 注入别的配置类
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
