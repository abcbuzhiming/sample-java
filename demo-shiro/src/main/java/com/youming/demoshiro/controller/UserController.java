package com.youming.demoshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

	// 日志生成器
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@ResponseBody
	@RequestMapping("/getInfo")
	public String getInfo() {
		return "Hello demo shrio !";
	}
	
	/**
	 * 使用拦截url的方式来测试权限，配置在Shrio**Config中ShiroFilterFactoryBean
	 * 如果你之前完全没有过登录（无cookie也无session），shiro的系统AuthorizationFilter会强制跳转login url，除非你自定义AuthorizationFilter
	 * 如果登录过，但是没有通过授权验证，AuthorizationFilter会向前端返回401页面，默认为/error地址，但是可以通过ShiroFilterFactoryBean的setUnauthorizedUrl方法改变
	 * 已知缺陷时，采取这种方式，你定义拦截链时无法定义逻辑关系(例:逻辑与，逻辑或)，默认的关系就是逻辑与，想重新定义，必须重写DefaultFilterChainManager类的splitChainDefinition方法
	 * 因此我们推荐使用注解的方式解决问题
	 * */
	
	@ResponseBody
	@RequestMapping("/testRoleFilter")
	public String testRoleFilter() {
		return "Hello testRoleFilter!";
	}
	
	@ResponseBody
	@RequestMapping("/testPermissionFilter")
	public String testPermissionFilter() {
		return "Hello testPermissionFilter!";
	}
	
	/**
	 * 注解可以用于类和method上
	 * 注解在已经登录过，有session或者cookie的前提下，如果验证没通过，抛出AuthorizationException异常
	 * 注解验证如果发现用户根本没有登录过，session和cookie都没有的情况下，会先抛出UnauthenticatedException异常，即没有登录
	 * */
	//@RequiresRoles("user")		//要求拥有user身份
	//@RequiresRoles(value= {"user","guest"})		//要求同时拥有user，guest身份
	@RequiresRoles(value= {"user","guest"},logical=Logical.OR)		//要求拥有user或guest身份
	@ResponseBody
	@RequestMapping("/testRoleAnnotation")
	public String testRoleAnnotation() {
		//Subject currentUser = SecurityUtils.getSubject();
		return "Hello testRoleAnnotation!";
	}
	
	//@RequiresPermissions("user:info:*")		//通配符表示法要求拥有user:info:分类下的所有权限
	//@RequiresPermissions("user:info:read")		//要求拥有user:info:read权限
	//@RequiresPermissions(value= {"user:info:read","user:info:insert"})		//要求同时拥有user:info:read和user:info:update权限
	@RequiresPermissions(value= {"user:info:read","user:info:insert"},logical=Logical.OR)		//要求拥有user:info:read或user:info:update权限
	@ResponseBody
	@RequestMapping("/testPermissionAnnotation")
	public String testPermissionAnnotation() {
		return "Hello testPermissionAnnotation!";
	}
	
	/**
	 * 多个realm时测试身份和权限
	 * */
	@ResponseBody
	@RequestMapping("/testMultipleRealm")
	public String testMultipleRealm() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.hasRole("student")) {
			logger.info("拥有student");
		}
		if (currentUser.hasRole("teacher")) {
			logger.info("拥有teacher");
		}
		if (currentUser.hasRole("admin")) {
			logger.info("拥有admin");
		}
		return "Hello testMultipleRealm!";
	}
	
}
