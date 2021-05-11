package com.youming.sample.spring.boot.cache.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
import com.github.benmanes.caffeine.cache.Caffeine;
import com.youming.sample.spring.boot.cache.po.UserRole;

/**
 * 配置两个缓存管理器一起使用Caffenine - Redis
 * 在实践中Caffenine作为1级缓存(进程内缓存基于内存，容量小但是速度更快)，Redis作为2级缓存(远程服务器请求，容量大但是相对速度慢一些)
 * 参考: springboot2.0 redis EnableCaching的配置和使用
 * http://www.cnblogs.com/hujunzheng/hujunzheng/p/9660681.html 参考: How to have
 * multiple cache manager configuration
 * https://stackoverflow.com/questions/48769886/how-to-have-multiple-cache-manager-configuration-in-multiple-modules-projects-sp
 */
@Configuration
public class CaffenineRedisConfig {

	@Primary
	@Bean("caffeineCacheManager")
	public CacheManager caffeineCacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		List<CaffeineCache> caches = new ArrayList<CaffeineCache>();
		// 第一个caffeine cache
		caches.add(new CaffeineCache("UserRole",
				Caffeine.newBuilder().recordStats().expireAfterWrite(1, TimeUnit.SECONDS) // 写后10s过期
						.expireAfterAccess(1, TimeUnit.SECONDS) // 读后10s过期
						.maximumSize(500) // 最大数量500
						.build()));

		cacheManager.setCaches(caches);
		return cacheManager;
	}

	@Bean("redisCacheManager")
	public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
		// 初始化一个RedisCacheWriter
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
		// 设置CacheManager的值序列化方式为JdkSerializationRedisSerializer,但其实RedisCacheConfiguration默认就是使用StringRedisSerializer序列化key，JdkSerializationRedisSerializer序列化value,所以以下注释代码为默认实现
		ClassLoader loader = this.getClass().getClassLoader();
		JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(loader);
		RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
				.fromSerializer(jdkSerializer);
		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(pair).entryTtl(Duration.ofSeconds(30)); // 设置默认超过期时间是30秒

		Jackson2JsonRedisSerializer<List> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<List>(
				List.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 序列化空值失败时不抛异常
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 反序列化不存在的字段失败时不抛异常


		// 初始化RedisCacheManager
		RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
		return cacheManager;
	}

	/**
	 * 2.0之前的配置方法
	 */
	/*
	 * @Bean public CacheManager cacheManager(RedisTemplate redisTemplate) {
	 * RedisCacheManager manager = new RedisCacheManager(redisTemplate);
	 * manager.setUsePrefix(true); RedisCachePrefix cachePrefix = new
	 * RedisPrefix("prefix"); manager.setCachePrefix(cachePrefix); // 整体缓存过期时间
	 * manager.setDefaultExpiration(3600L); // 设置缓存过期时间。key和缓存过期时间，单位秒 Map<String,
	 * Long> expiresMap = new HashMap<>(); expiresMap.put("user", 1000L);
	 * manager.setExpires(expiresMap); return manager; }
	 */

}
