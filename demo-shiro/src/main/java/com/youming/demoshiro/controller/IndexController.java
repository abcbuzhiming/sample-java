package com.youming.demoshiro.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.youming.demoshiro.utils.JsonWebTokenUtils;



@Controller
public class IndexController {

	@Autowired
	private Environment environment;
	
	@ResponseBody
	@RequestMapping("/")
	public String index() {
		return "Hello demo shrio !";
	}

	/**
	 * 登入
	 */
	@ResponseBody
	@RequestMapping("/doLogin")
	public String doLogin(@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "password", defaultValue = "") String password) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			return "不要重复登录";
		}
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
		usernamePasswordToken.setRememberMe(true); // 是否使用记住我功能，使用则返回rememberMe cookie，否则没有

		try {
			currentUser.login(usernamePasswordToken);
		} catch (UnknownAccountException | IncorrectCredentialsException ex) { // 账号名或密码错误
			// username wasn't in the system, show them an error message?
			System.out.println(ex.getMessage());
			String json = "账号名或密码错误";
			return json;
		} catch (LockedAccountException lae) { // 账号被锁定
			String json = "账号被冻结";
			return json;
		} catch (AuthenticationException ae) { // 未知错误
			// unexpected condition - error
			System.out.println(ae.getMessage());
			String json = "未知登录错误";
			return json;
		}
		return "登录成功";
	}

	/**
	 * 登出
	 */
	@ResponseBody
	@RequestMapping("/doLogout")
	public String doLogout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "登出成功";
	}

	@ResponseBody
	@RequestMapping("/doLoginStateless")
	public String doLoginStateless(@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "password", defaultValue = "") String password,HttpServletResponse response,HttpServletRequest request) {
		String device = "web"; // 这里没写完，如果客户端是移动端，需要传递一个参数标表明自己是移动端app
		int userId = 1;
		// 生成新的token
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, 30*60);
		Date expireTime = calendar.getTime();
		String accessToken = JsonWebTokenUtils.getJsonWebToken("admin", userId, device, date, expireTime);
		
		Cookie deleteToken = new Cookie("ACCESS_TOKEN", "deleteMe");		//删除旧cookie
		deleteToken.setPath(this.environment.getProperty("server.context-path"));
		response.addCookie(deleteToken);
		Cookie cookie = new Cookie("ACCESS_TOKEN",accessToken);		//设置客户端cookie
		cookie.setHttpOnly(true);
		int timeoutToken = 30 * 60 ;
		cookie.setMaxAge(timeoutToken);
		cookie.setPath(this.environment.getProperty("server.context-path"));
		response.addCookie(cookie);
		response.setCharacterEncoding("UTF-8");
//		response.setHeader("Content-type", "text/json;charset=UTF-8")
		return "登录成功";
	}

}
