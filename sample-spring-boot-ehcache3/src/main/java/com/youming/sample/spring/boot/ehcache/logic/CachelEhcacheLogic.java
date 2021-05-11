package com.youming.sample.spring.boot.ehcache.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 用于测试缓存的方法类
 */
@Component
public class CachelEhcacheLogic {

	private static final Logger logger = LoggerFactory.getLogger(CachelEhcacheLogic.class);
	
	/**
	 * 该注解用来标记请求命令返回的结果应该被缓存
	 * */
	@CacheResult(cacheName = "test")
	public String findPeople(@CacheKey String id) {
		logger.info("调用返回数据写入缓存的接口");
		return id;
	}
	
	/**
	 * 多个key的方式
	 * */
	@CacheResult(cacheName = "test")
	public String findPeople(@CacheKey String id,@CacheKey String userType) {
		
		return id + "-" + userType;
	}
	
	/**
	 * 写入缓存
	 * */
	@CachePut(cacheName = "test")
	public void putPeople(@CacheKey String id,@CacheValue String value) {
		//这里不需要额外的方法
	}
	
	/**
	 * 删除缓存
	 * */
	@CacheRemove(cacheName = "test")
	public void delPeople(@CacheKey String id) {
		//这里不需要额外的方法
	}
}
