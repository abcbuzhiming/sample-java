package com.youming.spring.boot.api.version.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.youming.spring.boot.api.version.condition.ApiVersioningRequestMappingHandlerMapping;

@Configuration
public class WebMvcRegistrationsAdapter implements WebMvcRegistrations {

	@Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiVersioningRequestMappingHandlerMapping();
    }

}
