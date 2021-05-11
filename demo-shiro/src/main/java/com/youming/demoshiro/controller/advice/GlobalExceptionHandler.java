package com.youming.demoshiro.controller.advice;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
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
	 * 异常时将被该方法拦截，拦截shiro未登陆异常
	 */
	@ExceptionHandler({ UnauthenticatedException.class })
	public void unloginHandler(HttpServletRequest request, HttpServletResponse response,
			UnauthenticatedException ex) {
		// System.out.println(ex.getClass().getName());
		System.out.println(ex.getMessage());
		
		//Map<String, Object> returnMap = SystemErrorMessage.getErrorMap(3);
		String returnMsg = "{\"code\":1,\"msg\":\"你需要验证身份\"}";
		response.setHeader("Content-type", "text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(returnMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 异常时将被该方法拦截，拦截shiro角色和授权异常
	 */
	@ExceptionHandler({ AuthorizationException.class })
	public void unPermissionsHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		System.out.println(ex.getMessage());
		
		//Map<String, Object> returnMap = SystemErrorMessage.getErrorMap(4);
		String returnMsg = "{\"code\":1,\"msg\":\"你没有权限访问\"}";
		response.setHeader("Content-type", "text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(returnMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * 异常时将被该方法拦截，拦截其它所有异常
	 */
	@ExceptionHandler({ Exception.class })
	public void errorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw, true)); // 打印完整堆栈信息到
		logger.error(sw.toString(), "SLF4J");
		
		//Map<String, Object> returnMap = SystemErrorMessage.getErrorMap(-1);
		//returnMap.put("message", ex.getMessage());
		//String returnMsg = DataTransform.toJson(returnMap);
		response.setHeader("Content-type", "text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write("系统错误");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
