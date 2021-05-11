package com.youming.springcloud.serviceuser.service.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.youming.springcloud.serviceuser.domain.Course;



/**
 * name 在使用FeignClient时，Spring会按name创建不同的ApplicationContext，通过不同的Context来隔离FeignClient的配置信息，在使用配置类时，不能把配置类放到Spring App Component scan的路径下，否则，配置类会对所有FeignClient生效
 * contextId 如果被定义，则取代name定义
 * path 所有接口访问的前置路径
 * */
@FeignClient(name = "service-course", contextId = "service-course", path = "/service-course/course")
//不要在接口上加@RequestMapping
public interface CourseService {
	
	@GetMapping(path = "/getAll", params = "!name")
	List<Course> getAll();
}
