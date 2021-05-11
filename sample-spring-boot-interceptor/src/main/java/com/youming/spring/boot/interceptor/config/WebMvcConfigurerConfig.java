package com.youming.spring.boot.interceptor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.youming.spring.boot.interceptor.handler.SampleHandlerInterceptor;

/**
 * 定义拦截器要拦截的地址
 * */
@Configuration
public class WebMvcConfigurerConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// LogInterceptor apply to all URLs.
		//registry.addInterceptor(new SampleHandlerInterceptor());
		
		//设置对哪些url起效
		//registry.addInterceptor(new SampleHandlerInterceptor()).addPathPatterns("/sample/*");
		
		//从起效的url中排除某些url
		registry.addInterceptor(new SampleHandlerInterceptor()).addPathPatterns("/sample/*")
				.excludePathPatterns("/sample/hello");

	}
}
