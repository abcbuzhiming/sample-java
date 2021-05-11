package com.youming.sample.cache.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * Ehcache在测试中不敌Caffeine
 */
public class MainEhcache {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("preConfigured", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class,
						String.class, ResourcePoolsBuilder.heap(10)))
				.build();
		cacheManager.init();

		Cache<Long, String> preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class);		//缓存管理器中获取指定的缓存
		//从缓存管理器中创建一个新的缓存
		Cache<Integer, String> myCache = cacheManager.createCache("myCache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Integer.class, String.class, ResourcePoolsBuilder.heap(10)).build());

		for (int i = 0; i <= 20; i++) {
			// 写
			myCache.put(i, "@" + i);
			// 读
			String value = myCache.get(i);
			System.out.println("get at " + i + ":" + value);
		}

		cacheManager.removeCache("preConfigured");		//移除缓存
		cacheManager.close();		//关闭缓存管理器
	}

}
