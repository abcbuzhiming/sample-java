package com.youming.sample.spring.boot.cache.controller;

import java.util.List;

import javax.xml.ws.RespectBinding;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youming.sample.spring.boot.cache.cache.UserRoleCache;
import com.youming.sample.spring.boot.cache.cache.two.UseRoleDoubleCache;
import com.youming.sample.spring.boot.cache.po.UserRole;
import com.youming.sample.spring.boot.cache.redis.UserRoleRedis;
import com.youming.sample.spring.boot.cache.utils.SpringUtils;



@Controller
@RequestMapping(value="/test")
public class TestController {

	//
	@ResponseBody
	@RequestMapping("/test")
	public String test() {
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
	
	
	@ResponseBody
	@RequestMapping("/redis")
	public String redis() {
		UserRoleRedis userRoleRedis = SpringUtils.getBean(UserRoleRedis.class);
		//UserRole userRole = userRoleRedis.getUserRole(0,"2001032");
		//return userRole.toString();
		List<UserRole> userRoleList = userRoleRedis.getUserRoleList(0,"2001032");
		System.out.println(userRoleList.get(0).getClass().toString());
		return userRoleList.toString();
		
	}
	
	@ResponseBody
	@RequestMapping("/test2")
	public String test2() {
		UseRoleDoubleCache useRoleDoubleCache = SpringUtils.getBean(UseRoleDoubleCache.class);
		List<UserRole> userRoleList = useRoleDoubleCache.getUserRoleL1(0,"2001032");
		return userRoleList.toString();
		
	}
}
