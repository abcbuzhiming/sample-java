package com.youming.spring.boot.shiro.javaconfig.controller;

import java.util.Collection;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youming.spring.boot.shiro.javaconfig.service.ShiroSampleService;
import com.youming.spring.boot.shiro.javaconfig.shiro.CustomRealm;
import com.youming.spring.boot.shiro.javaconfig.utils.SpringUtils;

@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private ShiroSampleService shiroSampleService;

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "hello youming-sample-spring-boot-shiro-start";
	}

	@ResponseBody
	@GetMapping("/login")
	public String login(@RequestParam(value = "username", defaultValue = "zhangsan") String username,
			@RequestParam(value = "password", defaultValue = "zhangsan") String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(true);
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(token);
		return "login success";
	}

	@ResponseBody
	@GetMapping("/logout")
	public String logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "logout success";
	}

	@ResponseBody
	@GetMapping("/read")
	public String read() {
		return this.shiroSampleService.read();
	}

	@ResponseBody
	@RequestMapping("/write")
	public String write() {
		return this.shiroSampleService.write();
	}

	/**
	 * 测试方法，展示shiro身份验证注解拦截器效果
	 */
	@RequiresAuthentication
	@ResponseBody
	@RequestMapping("/shiroAhc")
	public String shiroAhc() {
		Subject subject = SecurityUtils.getSubject();
		Object principal = subject.getPrincipal();
		String username = principal.toString();
		System.out.println("current user:" + username);
		return "shiroAhc证成功";
	}

	@RequiresRoles("admin")
	@ResponseBody
	@RequestMapping("/shiroAhzRoles")
	public String shiroAhzRoles() {
		Subject subject = SecurityUtils.getSubject();
		//subject.hasRole("admin");
		Object principal = subject.getPrincipal();
		String username = principal.toString();
		System.out.println("current user:" + username);
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
	
	/**
	 * 如何清除特定人的RealmCache，包括授权信息和验证信息
	 * 参考 http://shiro-user.582556.n2.nabble.com/Clearing-cached-permissions-for-a-different-user-td7578699.html
	 * */
	@ResponseBody
	@RequestMapping("/clearCache")
	public String clearCache() {
		CustomRealm customRealm = SpringUtils.getBean(CustomRealm.class);
		customRealm.clearCacheFor("zhangsan");
		return "clearCache OK";
	}

}
