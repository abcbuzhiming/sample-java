package com.youming.spring.boot.shiro.start.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "hello youming-sample-spring-boot-shiro-start";
	}

	
	/**
	 * 测试方法，展示shiro身份验证注解拦截器效果
	 */
	@RequiresAuthentication
	@ResponseBody
	@RequestMapping("/shiroAhc")
	public String shiroAhc() {
		return "shiroAhc证成功";
	}

	@RequiresRoles("data:student:buy")
	@ResponseBody
	@RequestMapping("/shiroAhzRoles")
	public String shiroAhzRoles() {
		return "shiroAhzRoles验证成功";
	}

	@RequiresPermissions("data:student:buy")
	@ResponseBody
	@RequestMapping("/shiroAhzPermissions")
	public String shiroAhzPermissions() {
		return "shiroAhzPermissions验证成功";
	}

	@RequiresPermissions(value = { "data:systemManage:update", "data:systemManage:read" }, logical = Logical.OR)
	@ResponseBody
	@RequestMapping("/shiroAhzPermissionsMultiple")
	public String shiroAhzPermissionsMultiple() {
		return "shiroAhzPermissionsMultiple验证成功";
	}

}
