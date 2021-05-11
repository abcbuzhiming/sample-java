package com.youming.demobootehcache.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youming.demobootehcache.logic.CachelEhcacheLogic;



@Controller
public class IndexController {
	// 日志生成器
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private CachelEhcacheLogic cachelEhcacheLogic;
	
	@ResponseBody
	@RequestMapping("/")
	public String index() {
		return "Hello demo-boot-ehcache3!";
	}
	
	
	@ResponseBody
	@RequestMapping("/read")
	public String read() {
		return this.cachelEhcacheLogic.findPeople("1");
		//return this.cachelEhcacheLogic.findPeople("1","teacher");
	}
	
	
	@ResponseBody
	@RequestMapping("/flush")
	public String updateCache() {
		this.cachelEhcacheLogic.putPeople("1", "123");
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/del")
	public String delCache() {
		this.cachelEhcacheLogic.delPeople("1");
		return "ok";
	}
}
