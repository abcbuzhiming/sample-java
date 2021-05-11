package com.youming.spring.boot.secure.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youming.spring.boot.secure.utils.SpringUtils;

/**
 * 自定义登录,这个方式并不成功，spring security的验证过程和servlet filter深度耦合，这种做法不合适
 * https://www.baeldung.com/manually-set-user-authentication-spring-security
 * */
@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/doLogin")
	@ResponseBody
	public String doLogin() {
		
		String name = "admin";
		String password = "123456";
		
		AuthenticationManager authenticationManager = SpringUtils.getBean(AuthenticationManager.class);
		try {
	        Authentication authentication = new UsernamePasswordAuthenticationToken(name, password);
	        Authentication result = authenticationManager.authenticate(authentication);		//登录过程
	        SecurityContextHolder.getContext().setAuthentication(result);		//返回并设置身份
	        
	    } catch(AuthenticationException e) {
	        System.out.println("Authentication failed: " + e.getMessage());
	    }
		
		return "login success";
	}
	
	
	@RequestMapping("/doLogout")
	@ResponseBody
    public String doLogout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		return "logout success";
	}
}
