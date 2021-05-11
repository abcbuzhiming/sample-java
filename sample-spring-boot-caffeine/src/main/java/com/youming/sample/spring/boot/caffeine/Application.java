package com.youming.sample.spring.boot.caffeine;

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
import com.youming.sample.spring.boot.caffeine.config.SpringApplicationConfig;
import com.youming.sample.spring.boot.caffeine.domain.CustomerInfoList;
import com.youming.sample.spring.boot.caffeine.domain.UserRole;





public class Application {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Hello sample-spring-boot-caffeine!");
		SpringApplication.run(SpringApplicationConfig.class, args);
	}

}
