package com.youming.sample.spring.boot.cache.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfigV1 {

	/**
	 * 2.0之前的配置方法
	 * 可以看到RedisCacheManager完全依赖redisTemplate进行数据的序列化/反序列化，读取和写入
	 * 参考:在SpringBoot中配置多个cache，实现多个cacheManager灵活切换 https://blog.csdn.net/s674334235/article/details/82593899
	 * */
	/* @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager manager = new RedisCacheManager(redisTemplate);
        manager.setUsePrefix(true);
        RedisCachePrefix cachePrefix = new RedisPrefix("prefix");
        manager.setCachePrefix(cachePrefix);
        // 整体缓存过期时间
        manager.setDefaultExpiration(3600L);
        // 设置缓存过期时间。key和缓存过期时间，单位秒
        Map<String, Long> expiresMap = new HashMap<>();
        expiresMap.put("user", 1000L);
        manager.setExpires(expiresMap);
        return manager;
    }*/
}
