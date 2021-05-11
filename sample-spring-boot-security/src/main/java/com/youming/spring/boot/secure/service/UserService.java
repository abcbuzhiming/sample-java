package com.youming.spring.boot.secure.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	
	public Map<String,String> loadUser(){
		logger.info("执行UserService.loadUser");
		Map<String, String> map = new HashMap<String, String>();
		map.put("type","0");
		map.put("username","admin");
		map.put("password","123456");
		return map;
	}
}
