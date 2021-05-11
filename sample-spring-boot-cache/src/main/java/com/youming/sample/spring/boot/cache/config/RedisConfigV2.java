package com.youming.sample.spring.boot.cache.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.youming.sample.spring.boot.cache.po.CustomerInfoList;
import com.youming.sample.spring.boot.cache.po.UserRole;

/**
 * 使用Redis作为缓存 参考：Springboot 2.X中Spring-cache与redis整合
 * https://blog.csdn.net/guokezhongdeyuzhou/article/details/79789629
 * ParameterizedType获取java泛型参数类型  https://blog.csdn.net/qq_18242391/article/details/54251947
 */
@Configuration
public class RedisConfigV2 {

	/**
	 * 2.0版本之后的RedisCacheManager初始化方式
	 */
	@Bean("redisCacheManager")
	CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {

		// 初始化一个RedisCacheWriter
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
		/**
		 * 设置CacheManager的值序列化方式为JdkSerializationRedisSerializer,
		 * RedisCacheConfiguration默认就是使用StringRedisSerializer序列化key，JdkSerializationRedisSerializer序列化value,所以以下注释代码为默认实现
		 */
		ClassLoader classLoader = this.getClass().getClassLoader();
		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer(
				classLoader);
		RedisSerializationContext.SerializationPair<Object> serializationPair = RedisSerializationContext.SerializationPair
				.fromSerializer(jdkSerializationRedisSerializer);
		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(serializationPair).entryTtl(Duration.ofSeconds(30));

		// Jackson2JsonRedisSerializer<UserRole> jackson2JsonRedisSerializer = new
		// Jackson2JsonRedisSerializer<UserRole>(
		// UserRole.class);
		//Jackson2JsonRedisSerializer<List> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<List>(List.class);
		// 试图传入List<ClassName>.class的迂回战术，但是其实没效，本质和list.class是一个性质，真实类型没有成功转换，用getClass能试出来
		// Jackson2JsonRedisSerializer<List<UserRole>> jackson2JsonRedisSerializer = new
		// Jackson2JsonRedisSerializer<List<UserRole>>(
		// (Class<List<UserRole>>) new ArrayList<UserRole>().getClass());
		//借助ParameterizedType的原理
		Jackson2JsonRedisSerializer<CustomerInfoList> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<CustomerInfoList>(
		CustomerInfoList.class);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 序列化空值失败时不抛异常
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 反序列化不存在的字段失败时不抛异常
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

		Map<String, RedisCacheConfiguration> initialCacheConfigurations = new HashMap<>();
		RedisCacheConfiguration redisCacheConfigurationUserRole = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
		initialCacheConfigurations.put("UserRole", redisCacheConfigurationUserRole);

		// 初始化RedisCacheManager
		RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig,
				initialCacheConfigurations);

		return cacheManager;
	}
}
