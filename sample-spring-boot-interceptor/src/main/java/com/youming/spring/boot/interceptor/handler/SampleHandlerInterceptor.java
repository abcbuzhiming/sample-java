package com.youming.spring.boot.interceptor.handler;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * 示例拦截器 
 * https://www.baeldung.com/spring-http-logging 如何获取请求流
 */
public class SampleHandlerInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(SampleHandlerInterceptor.class);

	/**
	 * 前置处理器
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HandlerMethod method = (HandlerMethod) handler;
		System.out.println("Controller name:" + method.getBean().getClass().getName()); // 拿到Controller的名称
		System.out.println("method name:" + method.getMethod().getName()); // 拿到method的名称

		long startTime = System.currentTimeMillis();
		System.out.println("\n-------- LogInterception.preHandle --- ");
		System.out.println("Request URL: " + request.getRequestURL());
		System.out.println("Start Time: " + System.currentTimeMillis());

		request.setAttribute("startTime", startTime);
		/*
		HttpServletRequest requestCacheWrapperObject = new ContentCachingRequestWrapper(request);		
		Map<String,String[]> paramMap = requestCacheWrapperObject.getParameterMap();
		for (Map.Entry<String, String[]> entry : paramMap.entrySet())  {
			System.out.println("Key = " + entry.getKey());
			String[] valueArr = entry.getValue();
			for (String value : valueArr) {
				System.out.println("value = " + value);
			}
		}*/
		return true;
	}

	/**
	 * 后置处理器
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, //
			Object handler, ModelAndView modelAndView) throws Exception {

		System.out.println("\n-------- LogInterception.postHandle --- ");
		System.out.println("Request URL: " + request.getRequestURI());

		// 可以在modelAndView中增加值或者考虑跳转

		HandlerMethod method = (HandlerMethod) handler;
		System.out.println("Controller name:" + method.getBean().getClass().getName()); // 拿到Controller的名称
		System.out.println("method name:" + method.getMethod().getName()); // 拿到method的名称

		
	}

	/**
	 * 连接结束回调 如果一个url被一个handler拦截两次，pre和post都会被调用两次，但是afterCompletion只会有一次
	 *
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, //
			Object handler, Exception ex) throws Exception {

		System.out.println("\n-------- LogInterception.afterCompletion --- ");
		/*
		 * long startTime = (Long) request.getAttribute("startTime"); long endTime =
		 * System.currentTimeMillis(); System.out.println("Request URL: " +
		 * request.getRequestURL()); System.out.println("End Time: " + endTime);
		 * 
		 * System.out.println("Time Taken: " + (endTime - startTime));
		 */
		String contextPath = request.getContextPath();
		logger.info("contextPath:" + contextPath);
		String servletPath = request.getServletPath();
		logger.info("servletPath:" + servletPath);
		int statusCode = response.getStatus();
		logger.info("statusCode:" + statusCode);
		
		HttpServletRequest requestCacheWrapperObject = new ContentCachingRequestWrapper(request);		//读取请求体		
		Map<String,String[]> paramMap = requestCacheWrapperObject.getParameterMap();
		for (Map.Entry<String, String[]> entry : paramMap.entrySet())  {
			System.out.println("Key = " + entry.getKey());
			String[] valueArr = entry.getValue();
			for (String value : valueArr) {
				System.out.println("value = " + value);
			}
		}

		//在拦截器中无法获取ResponseBody，必须用过滤器
		//https://stackoverflow.com/questions/8933054/how-to-read-and-copy-the-http-servlet-response-output-stream-content-for-logging
		//ContentCachingResponseWrapper contentCachingResponseWrapper=  new ContentCachingResponseWrapper(response);
	    //String result = new String(contentCachingResponseWrapper.getContentAsByteArray());
		//System.out.println("result:" + result);
		
	}
}
