package com.youming.demobootehcache.logic;

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

import org.springframework.stereotype.Component;

/**
 * 用于测试缓存的方法类
 */
@Component
public class CachelEhcacheLogic {

	@CacheResult(cacheName = "people")
	public String findPeople(@CacheKey String id) {
		String encoding = "UTF-8";
		File file = new File("F:\\测试url信息.txt");

		InputStreamReader reader;
		try {
			reader = new InputStreamReader(new FileInputStream(file));
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String result = "";
			String line = null;
			while ((line = br.readLine()) != null) {
				result += line; // 一次读入一行数据
			}
			System.out.println(result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 建立一个输入流对象reader
		return null;
	}
	
	
	@CacheResult(cacheName = "people")
	public String findPeople(@CacheKey String id,@CacheKey String userType) {
		String encoding = "UTF-8";
		File file = new File("F:\\测试url信息.txt");

		InputStreamReader reader;
		try {
			reader = new InputStreamReader(new FileInputStream(file));
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String result = "";
			String line = null;
			while ((line = br.readLine()) != null) {
				result += line; // 一次读入一行数据
			}
			System.out.println(result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 建立一个输入流对象reader
		return null;
	}
	
	/**
	 * 写入缓存
	 * */
	@CachePut(cacheName = "people")
	public void putPeople(@CacheKey String id,@CacheValue String value) {
		
	}
	
	/**
	 * 删除缓存
	 * */
	@CacheRemove(cacheName = "people")
	public void delPeople(@CacheKey String id) {
		
	}
}
