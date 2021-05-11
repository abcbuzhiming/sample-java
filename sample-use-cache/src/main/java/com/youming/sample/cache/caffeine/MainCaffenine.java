package com.youming.sample.cache.caffeine;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 深入剖析来自未来的缓存-Caffeine https://mp.weixin.qq.com/s/BH6vcUgI8na7iLaF0RGrSg
 * */
public class MainCaffenine {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cache<String, Object> manualCache = Caffeine.newBuilder()
		        .expireAfterWrite(10, TimeUnit.SECONDS)		//写入后10秒失效
		        .expireAfterAccess(10, TimeUnit.SECONDS)	//读后10秒失效
		        .maximumSize(10)
		        .build();
		
		manualCache.put("key", "value");
	}
}
