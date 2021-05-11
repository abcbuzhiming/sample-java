package com.youming.spring.boot.scheduling.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.Spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youming.spring.boot.scheduling.logic.AsyncLogic;
import com.youming.spring.boot.scheduling.utils.SpringUtils;





@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "hello youming-sample-spring-boot-scheduling";
	}

	
	@ResponseBody
	@RequestMapping("/async")
	public String async() {
		AsyncLogic asyncLogic = SpringUtils.getBean(AsyncLogic.class);
		asyncLogic.test();
		return "async ok";
	}
	
}
