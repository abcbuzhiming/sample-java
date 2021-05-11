package com.youming.spring.boot.thymeleaf.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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

import com.youming.spring.boot.thymeleaf.domain.User;



@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "hello youming-sample-spring-boot-thymeleaf";
	}

	
	/**
	 * 演示模板的效果
	 * */
	@RequestMapping("/view")
	public ModelAndView view() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("name", "thymeleaf");
		modelAndView.addObject("number1", 8.6f);
		modelAndView.addObject("number2", 2.6f);
		List<User> userlist = new ArrayList<User>();
		for(int i=0;i<5;i++) {
			User user = new User();
			user.setId(i + 1);
			user.setName(UUID.randomUUID().toString());
			userlist.add(user);
		}
		Map<String,String> mapData = new HashMap<String,String>();
		mapData.put("name", "姓名");
		mapData.put("age", "年龄");
		mapData.put("sex", "性别");
		mapData.put("birthday", "生日");
		mapData.put("phonenumber", "手机");
		
		
		modelAndView.addObject("mapData", mapData);
		modelAndView.addObject("user", userlist.get(2));
		modelAndView.addObject("users", userlist);
		modelAndView.addObject("dateTime",new Date());
		modelAndView.addObject("price",60);
		modelAndView.addObject("level","熟练");
		modelAndView.addObject("jsonStr","{\"aaa\":111}");
		modelAndView.addObject("html", "This is an &lt;em&gt;HTML&lt;/em&gt; text. &lt;b&gt;Enjoy yourself!&lt;/b&gt;");
		
		modelAndView.setViewName("/hello_view");	//返回的模板名称，模板被定义src/main/resources/templates目录下
		return modelAndView;
	}
}
