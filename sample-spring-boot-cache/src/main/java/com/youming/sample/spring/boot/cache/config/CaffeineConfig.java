package com.youming.sample.spring.boot.cache.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * Caffeine的JavaConfig方式 参考：http://lib.csdn.net/article/java/65506
 * Caffeine不支持对单个key设置过期时间
 * 
 */
@Configuration
public class CaffeineConfig {

	@Bean("caffeineCacheManager")
	public CacheManager caffeineCacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		List<CaffeineCache> caches = new ArrayList<CaffeineCache>();
		//第一个caffeine cache
		caches.add(new CaffeineCache("UserRole",
				Caffeine.newBuilder().recordStats().expireAfterWrite(10, TimeUnit.SECONDS) // 写后10s过期
						.expireAfterAccess(10, TimeUnit.SECONDS) // 读后10s过期
						.maximumSize(500) // 最大数量500
						.build()));
		//第二个caffeine cache
		caches.add(new CaffeineCache("cache2",
				Caffeine.newBuilder().recordStats().expireAfterWrite(10, TimeUnit.SECONDS) // 写后10s过期
						.expireAfterAccess(10, TimeUnit.SECONDS) // 读后10s过期
						.maximumSize(500) // 最大数量500
						.build()));

		cacheManager.setCaches(caches);

		return cacheManager;
	}
}
