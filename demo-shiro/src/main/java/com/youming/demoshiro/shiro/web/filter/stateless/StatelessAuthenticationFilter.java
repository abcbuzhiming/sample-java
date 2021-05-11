package com.youming.demoshiro.shiro.web.filter.stateless;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.youming.demoshiro.shiro.authc.StatelessToken;


/**
 * 无状态拦截器
 */
@Component
public class StatelessAuthenticationFilter extends AccessControlFilter {

	private static final Logger logger = LoggerFactory.getLogger(StatelessAuthenticationFilter.class);

	

	/**
	 * shiro拦截器以此判断用户请求能否被接受,如果返回false，onAccessDenied将被调用 这里直接返回false让系统调用使用jwt登录的过程
	 */
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse,
			Object mappedValue) {
		return false;
	}

	/**
	 * 连接被拒绝时的处理 token失效，要求重新登录
	 */
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		Cookie[] cookies = httpServletRequest.getCookies();
		String accessToken = null;
		if (null == cookies || cookies.length == 0) {
			logger.debug("没有得到token");
			this.needLoginResponse((HttpServletResponse) servletResponse);
			return false;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("ACCESS_TOKEN")) {
				accessToken = cookie.getValue();
				break;
			}
		}
		
		if (accessToken == null || accessToken.length() == 0) {
			logger.debug("没有得到token");
			this.needLoginResponse((HttpServletResponse) servletResponse);
			return false;
		}
		
		// 执行login过程
		Subject subject = SecurityUtils.getSubject();
		
		StatelessToken statelessToken  = new StatelessToken(accessToken);
		statelessToken.setRememberMe(false);
		try {
			subject.login(statelessToken);
		}catch (IncorrectCredentialsException e) {		//校验错误
			// TODO: handle exception
			logger.debug("token校验错误");
			this.needLoginResponse((HttpServletResponse) servletResponse);
			return false;
		} catch (ExpiredCredentialsException ex) { // 过期错误
			logger.debug("token过期");
			
			return false;
		} catch (LockedAccountException lae) { // 账号被锁定
			logger.debug("账号被锁定");
			this.needLoginResponse((HttpServletResponse) servletResponse);
			return false;
		} catch (AuthenticationException ae) { // 未知错误
			// unexpected condition - error?
			logger.debug("未知错误：" + ae.getMessage());
			this.needLoginResponse((HttpServletResponse) servletResponse);
			return false;
		}
		return true; // 继续处理接下来的filter
	}

	public void needLoginResponse(HttpServletResponse httpServletResponse) {
		
		//Cookie deleteToken = new Cookie(Constants.COOKIE_NAME_ACCESS_TOKEN, "deleteMe"); // 删除旧cookie
		//httpServletResponse.addCookie(deleteToken);
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setHeader("Content-type", "text/json;charset=UTF-8");
		String returnStr = "你需要登录";
		try {
			httpServletResponse.getWriter().write(returnStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
