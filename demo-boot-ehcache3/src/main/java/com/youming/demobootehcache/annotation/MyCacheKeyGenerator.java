package com.youming.demobootehcache.annotation;

import java.lang.annotation.Annotation;

import javax.cache.annotation.CacheKeyGenerator;
import javax.cache.annotation.CacheKeyInvocationContext;
import javax.cache.annotation.GeneratedCacheKey;

/**
 * 自定义的缓存key生成工具类
 * */
public class MyCacheKeyGenerator implements CacheKeyGenerator {

	@Override
	public GeneratedCacheKey generateCacheKey(
			CacheKeyInvocationContext<? extends Annotation> cacheKeyInvocationContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
