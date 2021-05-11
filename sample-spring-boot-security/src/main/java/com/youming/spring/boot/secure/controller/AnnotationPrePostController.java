package com.youming.spring.boot.secure.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Spring Security使用PrePost注解演示,最推荐的一种
 * @PreFilter, @PreAuthorize, @PostFilter, @PostAuthorize
 * */
@Controller
public class AnnotationPrePostController {
	
	/**
	 * 这个注解是无需登录任何人都可以访问
	 * */
	@PreAuthorize("isAnonymous()")
	@ResponseBody
	@RequestMapping("/helloPreAuthorizeIsAnonymous")
	public String helloPreAuthorizeIsAnonymous() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Credentials: " + authentication.getCredentials());		//类型是collection
		System.out.println("Principal: " + authentication.getPrincipal());
		System.out.println("Authorities: " + authentication.getAuthorities());		//类型是collection
		System.out.println("Details: " + authentication.getDetails());
		return "hello helloPreAuthorizeIsAnonymous";
	}
	
	/**
	 * 必须认证过才能访问
	 * */
	@PreAuthorize("authenticated")
	@ResponseBody
	@RequestMapping("/helloPreAuthorizeAuthenticated")
	public String helloPreAuthorizeAuthenticated() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Credentials: " + authentication.getCredentials());		//类型是collection
		System.out.println("Principal: " + authentication.getPrincipal());
		System.out.println("Authorities: " + authentication.getAuthorities());		//类型是collection
		System.out.println("Details: " + authentication.getDetails());
		return "hello helloPreAuthorizeAuthenticated";
	}
	
	/**
	 * prePost注解，前置处理
	 * 注意这两个hasAuthority('ROLE_USER')和hasRole('USER')是等价的
	 * */
	@PreAuthorize("hasRole('USER')")
	@ResponseBody
	@RequestMapping("/helloPreAuthorizeHasRole")
	public String helloPreAuthorizeHasRole() {
		
		/**
		 * 8.1.2 Core Components
		 * https://docs.spring.io/spring-security/site/docs/5.1.5.RELEASE/reference/htmlsingle/#securitycontextholder-securitycontext-and-authentication-objects
		 * SecurityContextHolder是spring
		 * secure的核心，它本身使用ThreadLocal绑定，是线程安全的，你可以在任何地方通过SecurityContextHolder的静态方法类获取principal（不会有null值），但是要注意的是，对某些异步线程的程序，这么做会引发问题
		 */
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Credentials: " + authentication.getCredentials());		//类型是collection
		System.out.println("Principal: " + authentication.getPrincipal());
		System.out.println("Authorities: " + authentication.getAuthorities());		//类型是collection
		System.out.println("Details: " + authentication.getDetails());
		return "hello helloPreAuthorizeHasRole";
	}
	
	
	/**
	 * prePost注解，前置处理
	 * 注意hasAuthority('ROLE_USER')和hasRole('USER')是等价的
	 * */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@ResponseBody
	@RequestMapping("/helloPreAuthorizeHasAuthority")
	public String helloPreAuthorizeHasAuthority() {
		/**
		 * 8.1.2 Core Components
		 * https://docs.spring.io/spring-security/site/docs/5.1.5.RELEASE/reference/htmlsingle/#securitycontextholder-securitycontext-and-authentication-objects
		 * SecurityContextHolder是spring
		 * secure的核心，它本身使用ThreadLocal绑定，是线程安全的，你可以在任何地方通过SecurityContextHolder的静态方法类获取principal（不会有null值），但是要注意的是，对某些异步线程的程序，这么做会引发问题
		 */
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Credentials: " + authentication.getCredentials());		//类型是collection
		System.out.println("Principal: " + authentication.getPrincipal());
		System.out.println("Authorities: " + authentication.getAuthorities());		//类型是collection
		System.out.println("Details: " + authentication.getDetails());
		return "hello helloPreAuthorizeHasAuthority";
	}
	
	
	/**
	 * hasPermission里的权限其实仍然是Role一类的东西
	 * */
	//@PreAuthorize("hasPermission(#contact, 'admin')")
	
	
	//@PreAuthorize("#c.name == authentication.name")
	//public void doSomething(@P("c") Contact contact)
		
	
	//@PreAuthorize("#contact.name == authentication.name")
	//public void doSomething(Contact contact);
	
	//@PreAuthorize("isAnonymous()")
	
	
	//Spring Security支持过滤集合和数组，现在可以使用表达式实现。 这通常是在方法的返回值上执行的
	//使用@PostFilter批注时，Spring Security会遍历返回的集合并删除所提供的表达式为false的所有元素。 名称filterObject是指集合中的当前对象。 
	//您也可以在方法调用之前使用@PreFilter进行过滤，尽管这是一个不太常见的要求。 语法是相同的，但如果有多个参数是集合类型，则必须使用此批注的filterTarget属性按名称选择一个。
	//@PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, 'admin')")
}	
