package com.youming.demoshiro.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.youming.demoshiro.utils.SpringUtils;

@Configuration
@EnableAutoConfiguration // spring boot将根据你添加的依赖自动进行配置
@ComponentScan(basePackages = { "com.youming.demoshiro" }) // 自动扫描包范围,相当于xml配置:context:component-scan
@EnableAspectJAutoProxy(proxyTargetClass = true) // 指定使用CGLIB代理，aop，相当于xml配置:<aop:aspectj-autoproxy
// proxy-target-class="true"/>
@EnableTransactionManagement // 事务
@PropertySource({ "classpath:application.properties","classpath:redis.properties" }) // 注入多个配置文件如果是相同的key，则最后一个起作用
//@Import({ ShiroBaseConfig.class }) // 注入shiro基本配置类
//@Import({ ShiroMultipleRealmConfig.class }) // 注入shiro配置类,多个Realm模式
//@Import({ShiroStatelessConfig.class }) // 注入shiro配置类,无状态模式，基于JWT保存在cookie
@Import({ShiroDaoRedisConfig.class,RedisCacheConfig.class }) // 注入shiro配置类,使用redis作为Session存储
public class ApplicationConfig {

	// 专门用于注入保留ApplicationContext的工具类
	@Bean(name = "springUtils")
	public SpringUtils springUtils() {
		SpringUtils springUtils = new SpringUtils();
		return springUtils;
	}
}
