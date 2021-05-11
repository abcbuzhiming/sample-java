package com.youming.demoshiro;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.session.mgt.SimpleSession;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.youming.demoshiro.config.ApplicationConfig;


/**
 * shiro的特点
 * 认证(Authentication)和授权(Authorization)两部分，两部分互相隔离
 * 认证和session挂钩，session失效则失效
 * 授权可以和cookie挂钩，cookie保存在客户端，长期有效
 * shiro的授权，分为按角色和按权限两种
 * shiro实现的验证判断有两种，配置在拦截url中的验证字符串，和配置在方法上的注解。但是验证字符串不能实现“或”的逻辑关系，只有注解模式可以
 * */
public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println( "Hello demo-shiro!" );
		ApplicationContext applicationContext = SpringApplication.run(ApplicationConfig.class, args); 
		
	}

}
