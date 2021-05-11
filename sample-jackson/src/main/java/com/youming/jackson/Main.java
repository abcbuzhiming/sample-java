package com.youming.jackson;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 *  create by 夏巍
 *  关于json字符串中的引号问题
 *  当你把含有引号内容的字符串作为内容加入json，则这些引号会被json加上斜杠做转义
 * */
public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disableDefaultTyping(); // 不保存类型属性
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 序列化空值失败时不抛异常
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 反序列化不存在的字段失败时不抛异常
		
		Map<String, String> aMap = new HashMap<String, String>();
		aMap.put("1", "asdasd");
		String aJson = objectMapper.writeValueAsString(aMap);
		System.out.println(aJson);
		
		Map<String, String> bMap = new HashMap<String, String>();
		bMap.put("1", "asdasdasfsdf");
		bMap.put("2", aJson);
		String bJson = objectMapper.writeValueAsString(bMap);
		System.out.println(bJson);
		
		Map<String, Object> cMap = new HashMap<String, Object>();
		cMap.put("1", "12sdasdasd");
		cMap.put("2", aMap);
		String cJson = objectMapper.writeValueAsString(cMap);
		System.out.println(cJson);
	}

}
