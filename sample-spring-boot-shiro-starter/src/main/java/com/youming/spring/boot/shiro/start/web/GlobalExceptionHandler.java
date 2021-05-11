package com.youming.spring.boot.shiro.start.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;






/**
 * 全局异常处理，捕获所有Controller中抛出的异常。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 如果请求时未提交任何验证信息(比如cookie),会触发这个异常
	 * 如果只有rememberMe cookie但是访问的方法需要authc(比如有@RequiresAuthentication注解或者authc拦截器作用的url)，也会触发
	 * 因此这里需要根据情况返回特定的信息
	 */
	@ExceptionHandler({ UnauthenticatedException.class })
	public void unAuthenticatedHandler(HttpServletRequest request, HttpServletResponse response,
			UnauthenticatedException ex) {
		// System.out.println(ex.getClass().getName());
		System.out.println(ex.getMessage());
		
	}

	/**
	 * 异常时将被该方法拦截，拦截shiro角色和授权异常
	 */
	@ExceptionHandler({ AuthorizationException.class })
	public void unPermissionsHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		System.out.println(ex.getMessage());
		
		
	}
	
	/**
	 * 处理mvc参数校验错误
	 * */
	@ExceptionHandler({ ConstraintViolationException.class })
	public void errorParamHandler(HttpServletRequest request, HttpServletResponse response, ConstraintViolationException ex) {
		logger.info("错误类名：" + ex.getClass().getName());
		
		
	}
	/**
	 * 异常时将被该方法拦截，拦截其它所有异常
	 */
	@ExceptionHandler({ Exception.class })
	public void errorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		StringWriter sw = new StringWriter();
		
	}
	
}
