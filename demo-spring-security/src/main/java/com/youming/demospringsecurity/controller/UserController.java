package com.youming.demospringsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

	@ResponseBody
	@RequestMapping("/getInfo")
	public String getInfo() {
		/**
		 * 参考: 9.2.1 SecurityContextHolder, SecurityContext and Authentication Objects
		 * https://docs.spring.io/spring-security/site/docs/4.2.4.RELEASE/reference/htmlsingle/#securitycontextholder-securitycontext-and-authentication-objects
		 * SecurityContextHolder是spring
		 * secure的核心，它本身使用ThreadLocal绑定，是线程安全的，你可以在任何地方通过SecurityContextHolder的静态方法类获取principal（不会有null值），但是要注意的是，对某些异步线程的程序，这么做会引发问题
		 */
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Credentials: " + authentication.getCredentials());		//类型是collection
		System.out.println("Principal: " + authentication.getPrincipal());
		System.out.println("Authorities: " + authentication.getAuthorities());		//类型是collection
		System.out.println("Details: " + authentication.getDetails());
		// String username =
		// SecurityContextHolder.getContext().getAuthentication().getName();
		return "getInfo success";
	}
	
	/**
	 * 参考:@Pre and @Post Annotations
	 * https://docs.spring.io/spring-security/site/docs/4.2.4.RELEASE/reference/htmlsingle/#el-pre-post-annotations
	 * */
	
	//@PreAuthorize("authenticated")
	//@PreAuthorize("isAnonymous()")
	@PreAuthorize("hasRole('USER')")
	//@PreAuthorize("hasPermission(#contact, 'admin')")
	//@PreAuthorize("hasAuthority('ROLE_TELLER')")
	@ResponseBody
	@RequestMapping("/testRole")
	public String testRole() {
		
		return "testRole success";
	}
}
