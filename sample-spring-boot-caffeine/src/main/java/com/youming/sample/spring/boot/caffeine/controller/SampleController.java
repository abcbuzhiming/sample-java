package com.youming.sample.spring.boot.caffeine.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youming.sample.spring.boot.caffeine.cache.UserRoleCache;
import com.youming.sample.spring.boot.caffeine.domain.UserRole;
import com.youming.sample.spring.boot.caffeine.utils.SpringUtils;



@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "hello youming-sample-spring-boot-caffeine";
	}

	/**
	 * 使用缓存
	 * */
	@ResponseBody
	@RequestMapping("/getRoleCache")
	public String getRoleCache() {
		UserRoleCache userRoleCache = SpringUtils.getBean(UserRoleCache.class);
		List<UserRole> userRoleList = userRoleCache.getUserRole(0,"2001032");
		return userRoleList.toString();
		
	}
	
	/**
	 * 删除缓存
	 * */
	@ResponseBody
	@RequestMapping("/del")
	public String del() {
		UserRoleCache userRoleCache = SpringUtils.getBean(UserRoleCache.class);
		userRoleCache.delUserRoleCache(0,"2001032");
		return "del ok";
	}
}
