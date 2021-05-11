package com.youming.sample.spring.boot.cache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.youming.sample.spring.boot.cache.config.SpringApplicationConfig;
import com.youming.sample.spring.boot.cache.po.CustomerInfoList;
import com.youming.sample.spring.boot.cache.po.UserRole;





public class App {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Hello sample-spring-boot-cache!");
		SpringApplication.run(SpringApplicationConfig.class, args);
		/*
		List<UserRole> userRoleList = new ArrayList<UserRole>();
		UserRole userRole = new UserRole();
		userRole.setUserNumber("123123");
		userRoleList.add(userRole);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 序列化空值失败时不抛异常
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 反序列化不存在的字段失败时不抛异常
		String json = objectMapper.writeValueAsString(userRoleList);
		System.out.println(json);
		@SuppressWarnings("unchecked")
		List<UserRole> userRoles = objectMapper.readValue(json, List.class);
//		List<UserRole> userRoles = objectMapper.readValue(json, new TypeReference<List<UserRole>>() {
//		});
		System.out.println(userRoles.toString());
		System.out.println(userRoles.get(0).getClass().toString());
		
		*/
		
		/*
		 * 通过 getGenericSuperclass和ParameterizedType.getActualTypeArguments拿父类的泛型参数类型的办法，
		 * Jackson2JsonRedisSerializer利用这个原理绕开了Java的泛型实现是类型擦除，无法传递类似List<ClassName>的类型参数的问题
		ParameterizedType parameterizedType = (ParameterizedType)CustomerInfoList.class.getGenericSuperclass();
		Type[] types = parameterizedType.getActualTypeArguments();
		System.out.println(types[0].getTypeName());
		*/
	}

}
