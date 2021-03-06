package com.youming.spring.boot.scheduling.config;

import java.awt.MenuComponent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
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
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.youming.spring.boot.scheduling.utils.SpringUtils;



@SpringBootApplication
@ComponentScan(basePackages = { "com.youming.spring.boot.scheduling" }, excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = Configuration.class) }) // 自动扫描包范围,相当于xml配置:context:component-scan
@Import({ AsyncConfigurerImpl.class,SchedulingConfigurerImpl.class }) // 引入其他的配置类
public class InContainerConfig extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder) {
		return springApplicationBuilder.sources(InContainerConfig.class);
	}

	@Autowired
	private Environment environment;

	// 专门用于注入保留ApplicationContext的工具类
	@Bean(name = "springUtils")
	public SpringUtils springUtils() {
		SpringUtils springUtils = new SpringUtils();
		return springUtils;
	}

}
