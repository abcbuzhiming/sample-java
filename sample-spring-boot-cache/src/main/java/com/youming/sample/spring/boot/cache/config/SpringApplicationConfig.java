package com.youming.sample.spring.boot.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.env.Environment;

import com.youming.sample.spring.boot.cache.utils.SpringUtils;



@Configuration
@EnableAutoConfiguration // spring boot将根据你添加的依赖自动进行配置
@ComponentScan(basePackages = { "com.youming.sample.spring.boot.cache" }, excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = Configuration.class) }) // 自动扫描包范围,相当于xml配置:context:component-scan
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableCaching // 使用进程级缓存
@PropertySource({"classpath:redis.properties" })		//插入redis配置文件
@Import({ RedisConfigV2.class, }) // 注入缓存配置类
public class SpringApplicationConfig {

	@Autowired
	private Environment environment;

	// 专门用于注入保留ApplicationContext的工具类
	@Bean(name = "springUtils")
	public SpringUtils springUtils() {
		SpringUtils springUtils = new SpringUtils();
		return springUtils;
	}
}
