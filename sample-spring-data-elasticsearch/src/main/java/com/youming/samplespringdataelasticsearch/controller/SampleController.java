package com.youming.samplespringdataelasticsearch.controller;

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

import com.youming.samplespringdataelasticsearch.domain.User;



@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		//logger.info("sample/hello " + System.currentTimeMillis());
		return "hello sample-spring-data-elasticsearch";
	}
	
	
}
