package com.youming.commerceerp.admin.config;

import java.time.Duration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

/**
 * 多个Redis实例配置
 */
@Configuration
public class RedisConfig {

	@Autowired
	private Environment environment;

	public RedisConnectionFactory createJedisConnectionFactory() {

		// redis配置
		RedisConfiguration redisConfiguration = new RedisStandaloneConfiguration(
				environment.getProperty("spring.redis.host"),
				Integer.valueOf(environment.getProperty("spring.redis.port")));
		((RedisStandaloneConfiguration) redisConfiguration).setDatabase(0);		//选择数据库
		((RedisStandaloneConfiguration) redisConfiguration)
				.setPassword(environment.getProperty("spring.redis.password"));

		// 连接池配置
		
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxIdle(Integer.valueOf(environment.getProperty("spring.redis.lettuce.pool.max-idle")));
		genericObjectPoolConfig.setMinIdle(Integer.valueOf(environment.getProperty("spring.redis.lettuce.pool.min-idle")));
		genericObjectPoolConfig.setMaxTotal(Integer.valueOf(environment.getProperty("spring.redis.lettuce.pool.max-active")));
		genericObjectPoolConfig.setMaxWaitMillis(Long.valueOf(environment.getProperty("spring.redis.lettuce.pool.max-wait")));
		
		// redis客户端配置
		LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration
				.builder()
				.commandTimeout(Duration.ofMillis(Long.valueOf(environment.getProperty("spring.redis.timeout"))));

		builder.shutdownTimeout(Duration.ofMillis(10000));
		builder.poolConfig(genericObjectPoolConfig);
		LettuceClientConfiguration lettuceClientConfiguration = builder.build();

		// 根据配置和客户端配置创建连接
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration,
				lettuceClientConfiguration);
		lettuceConnectionFactory.afterPropertiesSet();

		return lettuceConnectionFactory;

	}
}
