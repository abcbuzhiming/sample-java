package com.youming.spring.boot.freemarker.controller;

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

import com.youming.spring.boot.freemarker.domain.User;



@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		logger.info("sample/hello " + System.currentTimeMillis());
		return "hello sample-spring-boot-freemarker";
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public String get() {
		logger.info("sample/get " + System.currentTimeMillis());
		return "sample get";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public String update() {
		logger.info("sample/update " + System.currentTimeMillis());
		return "sample update";
	}
	

	
	/**
	 * 演示模板的效果
	 * */
	@RequestMapping("/view")
	public ModelAndView view() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("name", "FreeMarker");
		modelAndView.addObject("number1", 8.6f);
		modelAndView.addObject("number2", 2.6f);
		List<User> userlist = new ArrayList<User>();
		for(int i=0;i<5;i++) {
			User user = new User();
			user.setId(i + 1);
			user.setName(UUID.randomUUID().toString());
			userlist.add(user);
		}
		modelAndView.addObject("users", userlist);
		modelAndView.addObject("dateTime",new Date());
		
		modelAndView.setViewName("/hello_view");	//返回的模板名称，模板被定义src/main/resources/templates目录下
		return modelAndView;
	}
}
