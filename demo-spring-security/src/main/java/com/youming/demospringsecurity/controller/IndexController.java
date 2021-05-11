package com.youming.demospringsecurity.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

	@ResponseBody
	@RequestMapping("/")
	public String index() {
		return "Hello demo spring security !";
	}

	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@RequestMapping("/logout")
	public String logout() {
		return "logout";
	}
	
	
	@RequestMapping("/index")
	public String indexThymeleaf() {
		return "index";
	}
	
	/**
	 * 登入
	 */
	@ResponseBody
	@RequestMapping("/doLogin")
	public String doLogin(@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "password", defaultValue = "") String password) {
		/*
		Authentication request = new UsernamePasswordAuthenticationToken(userName, password);
		Authentication result = authenticationManager.authenticate(request);
		SecurityContextHolder.getContext().setAuthentication(result);
		*/
		return "登录成功";
		
	}
}
