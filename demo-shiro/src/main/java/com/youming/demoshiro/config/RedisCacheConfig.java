package com.youming.demoshiro.config;

import java.sql.SQLException;

import javax.activation.DataSource;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.youming.demoshiro.utils.SpringUtils;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;



@EnableCaching // 启用缓存，这个注解很重要
public class RedisCacheConfig extends CachingConfigurerSupport {

	@Autowired
	private SpringUtils springUtils; // 必须先注入这个，否则spring上下文就是空的

	
	@Bean(name = "redisTemplateSession")
	public RedisTemplate<String, String> redisTemplateSession() {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		RedisConnectionFactory redisConnectionFactory = this.springUtils.getApplicationContext()
				.getBean(RedisConnectionFactory.class);
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		

		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enableDefaultTyping(DefaultTyping.OBJECT_AND_NON_CONCRETE); // 在序列化后的数据里保存对象的类信息
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 序列化空值失败时不抛异常
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 反序列化不存在的字段失败时不抛异常
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
		//redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

		return redisTemplate;
	}

	/**
	 * 缓存管理器.
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public CacheManager cacheManager() {
		CacheManager cacheManager = new RedisCacheManager(this.redisTemplateSession());
		return cacheManager;
	}
}
