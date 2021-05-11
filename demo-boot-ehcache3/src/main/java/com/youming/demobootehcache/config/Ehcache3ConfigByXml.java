package com.youming.demobootehcache.config;

import java.util.concurrent.TimeUnit;

import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.stereotype.Component;

/**
 * 实现一个基于ehcache的JCacheManager接口实现，这个实现是依赖于ehcache xml的配置
 */
@Component
public class Ehcache3ConfigByXml implements JCacheManagerCustomizer {

	@Override
	public void customize(CacheManager cacheManager) {
		// TODO Auto-generated method stub
		cacheManager.createCache("people",
				new MutableConfiguration<>()
						.setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 60)))
						.setStoreByValue(false).setStatisticsEnabled(true));
	}

}
