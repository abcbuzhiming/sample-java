package com.youming.bootjsonbody.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.youming.bootjsonbody.filter.RequestJsonFilter;
import com.youming.bootjsonbody.support.RequestJsonHandler;

@Configuration
public class CustomeWebMvcConfigurer implements WebMvcConfigurer {

	@Autowired
    private RequestJsonHandler requestJsonHandler;

   // 把requestJson解析器也交给spring管理
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(0,requestJsonHandler);
    }
    
    
    @Bean
    public FilterRegistrationBean<RequestJsonFilter> filterRegister() {
        FilterRegistrationBean<RequestJsonFilter> registration = new FilterRegistrationBean<RequestJsonFilter>();
        registration.setFilter(new RequestJsonFilter());
        //拦截路径
        registration.addUrlPatterns("/*");
        //过滤器名称
        registration.setName("requestJsonFilter");
        //过滤器顺序,需排在第一位
        registration.setOrder(1);
        return registration;
    }
    
}
