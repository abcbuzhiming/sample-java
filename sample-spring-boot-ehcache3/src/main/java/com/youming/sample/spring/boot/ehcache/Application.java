package com.youming.sample.spring.boot.ehcache;

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
import com.youming.sample.spring.boot.ehcache.config.SpringApplicationConfig;



/**
 * spring boot 以JSR107标准调用ehcache3
 * 总的来的说个人认为JSR107标准的注解没有Spring自己实现的那一套直观
 * 
 * */


public class Application {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Hello sample-spring-boot-ehcache3");
		SpringApplication.run(SpringApplicationConfig.class, args);
	}

}
