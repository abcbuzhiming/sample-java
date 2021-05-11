package com.youming.spring.boot.secure.web.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.youming.spring.boot.secure.security.authentication.CustomUsernamePasswordAuthenticationToken;

/**
 * 仿照自UsernamePasswordAuthenticationFilter的自定义过滤器
 */
public class CustomUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private Logger logger = LoggerFactory.getLogger(CustomUsernamePasswordAuthenticationFilter.class);
	
	
	public static final String SPRING_SECURITY_FORM_TYPE_KEY = "type"; // 用户类型
	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

	private String typeParameter = SPRING_SECURITY_FORM_TYPE_KEY;
	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

	// 使用该端点进行ip认证
	public CustomUsernamePasswordAuthenticationFilter() {
		super(new AntPathRequestMatcher("/doLoginCustom"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// TODO Auto-generated method stub
		logger.info("执行CustomUsernamePasswordAuthenticationFilter.attemptAuthentication");
		
		Integer type = 0;
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		String typeStr = obtainType(request);
		if (typeStr != null && typeStr.length() > 0) {
			type = Integer.valueOf(typeStr);
		}
		if (username == null) {
			username = "";
		}
		username = username.trim();

		if (password == null) {
			password = "";
		}
		
		
		CustomUsernamePasswordAuthenticationToken customUsernamePasswordAuthenticationToken = new CustomUsernamePasswordAuthenticationToken(
				type, username, password);
		// Allow subclasses to set the "details" property
		//setDetails(request, customUsernamePasswordAuthenticationToken);
		return this.getAuthenticationManager().authenticate(customUsernamePasswordAuthenticationToken);
	}

	private String obtainType(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(typeParameter);
	}

	private String obtainUsername(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(usernameParameter);
	}

	private String obtainPassword(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(passwordParameter);
	}

}
