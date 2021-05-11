package com.youming.bootjsonbody.filter;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.youming.bootjsonbody.utils.IOUtils;

/**
 * 注册过滤器，包装ServletRequest，为了多次使用getReader
 * */
//@Order(1)		//顺序
//@WebFilter(filterName = "requestJsonFilter", urlPatterns = "/*")		//过滤器名称，拦截路径
public class RequestJsonFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(RequestJsonFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	/**
     * 用来对request中的Body数据进一步包装
     * @param req
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		JsonContentCachingRequestWrapper jsonContentCachingRequestWrapper = null;
		if(request instanceof HttpServletRequest) {
			/**
             * 只是为了防止一次请求中调用getReader(),getInputStream(),getParameter()
             * 都清楚inputStream 并不具有重用功能，即多次读取同一个inputStream流，
             * 只有第一次读取时才有数据，后面再次读取inputStream 没有数据，
             * 即，getReader()，只能调用一次，但getParameter()可以调用多次，详情可见ContentCachingRequestWrapper源码
              */
			
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            jsonContentCachingRequestWrapper = new JsonContentCachingRequestWrapper(httpServletRequest);
            String body = IOUtils.toString(jsonContentCachingRequestWrapper.getBody(),request.getCharacterEncoding());
            logger.info("body:" + body);            
        }
		
		/*
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		BufferedReader reader = new BufferedReader(new InputStreamReader(requestWrapper.getInputStream(), Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonDate = sb.toString();
        logger.info("jsonDate: " + jsonDate);
        */
		/*
		byte[] buf = requestWrapper.getContentAsByteArray();
        if (buf.length > 0) {
            String payload = new String(buf, 0, buf.length, requestWrapper.getCharacterEncoding());
            System.out.println("payload:" + payload);
        }
        */
        
		//chain.doFilter(jsonContentCachingRequestWrapper == null ? request : jsonContentCachingRequestWrapper, response);
		chain.doFilter(jsonContentCachingRequestWrapper, response);
		
        
        
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
