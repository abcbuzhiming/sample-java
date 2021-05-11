package com.youming.demospringsecurity.security.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 自定义的AuthenticationEntryPoint
 * https://docs.spring.io/spring-security/site/docs/4.2.4.RELEASE/reference/htmlsingle/#auth-entry-point
 * 如果用户请求安全的HTTP资源但未通过身份验证，则将调用AuthenticationEntryPoint
 * 默认的配置中，Formlogin是LoginUrlAuthenticationEntryPoint这个实现，这个实现会导致页面跳转到登录页
 * 在这里，我们只发错误提示，不跳转
 * */
@Component( "restAuthenticationEntryPoint" )
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized:" + authException.getLocalizedMessage());
	}

}
