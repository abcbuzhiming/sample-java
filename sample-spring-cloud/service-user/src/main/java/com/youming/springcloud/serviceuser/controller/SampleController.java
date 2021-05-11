package com.youming.springcloud.serviceuser.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youming.springcloud.serviceuser.domain.Course;
import com.youming.springcloud.serviceuser.service.feign.CourseService;
import com.youming.springcloud.serviceuser.utils.JsonTransformUtils;




@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@Autowired
    private CourseService courseService;

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		logger.info("sample/hello " + System.currentTimeMillis());
		return "hello sample-spring-cloud-service-user";
	}
	
	
	
	@ResponseBody
	@RequestMapping("/feignGetCourse")
	public String feignGetCourse() {
		List<Course> list =  courseService.getAll();
		return JsonTransformUtils.toJson(list);
	}
	
}
