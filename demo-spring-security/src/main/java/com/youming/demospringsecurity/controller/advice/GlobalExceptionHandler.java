package com.youming.demospringsecurity.controller.advice;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * 全局异常处理，捕获所有Controller中抛出的异常。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	
	
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
	
	/**
	 * spring security 拒绝访问异常 
	 * */
	
	@ExceptionHandler({ AccessDeniedException.class })
	public void accessDeniedExceptionHandler(HttpServletRequest request, HttpServletResponse response,AccessDeniedException exception) {
		//exception.printStackTrace();
		logger.info(exception.getMessage());
		response.setHeader("Content-type", "text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String returnMsg = exception.getMessage();
		try {
			response.getWriter().write(returnMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
