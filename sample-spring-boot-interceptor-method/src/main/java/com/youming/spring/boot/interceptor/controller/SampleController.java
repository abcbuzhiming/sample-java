package com.youming.spring.boot.interceptor.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.Spring;

import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youming.spring.boot.interceptor.annotation.CustomAfterAnnotation;
import com.youming.spring.boot.interceptor.annotation.CustomAfterReturningAnnotation;
import com.youming.spring.boot.interceptor.annotation.CustomAfterThrowingAnnotation;
import com.youming.spring.boot.interceptor.annotation.CustomAroundAnnotation;
import com.youming.spring.boot.interceptor.annotation.CustomBeforeAnnotation;
import com.youming.spring.boot.interceptor.utils.SpringUtils;





@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "hello youming-sample-spring-boot-interceptor-method";
	}

	
	@CustomBeforeAnnotation("CustomBeforeAnnotation")
	@ResponseBody
	@RequestMapping("/testBeforeAOP")
	public String testBeforeAOP(String param1,String param2) {
		logger.info("hello testBeforeAOP：" + param1 + "|" + param2);
		return "hello testBeforeAOP";
	}
	
	@CustomAfterAnnotation("CustomAfterAnnotation")
	@ResponseBody
	@RequestMapping("/testAfterAOP")
	public String testAfterAOP(String param) {
		logger.info("hello testAfterAOP：" + param);
		return "hello testAfterAOP";
	}
	
	@CustomAfterReturningAnnotation
	@ResponseBody
	@RequestMapping("/testAfterReturningAOP")
	public String testAfterReturningAOP(String param) {
		logger.info("hello testAfterReturningAOP：" + param);
		return "hello testAfterReturningAOP";
	}
	
	@CustomAfterThrowingAnnotation
	@ResponseBody
	@RequestMapping("/testAfterThrowingAOP")
	public String testAfterThrowingAOP() throws IOException {
		logger.info("hello testAfterThrowingAOP");
		boolean b= true;
		if (b) {
			throw new IOException("aaa");
		}
		
		return "hello testAfterThrowingAOP";
	}
	
	
	@CustomAroundAnnotation
	@ResponseBody
	@RequestMapping("/testAroundAOP")
	public String testAroundAOP(String param1,String param2) {
		logger.info("hello testAroundAOP：" + param1 + "|" + param2);
		return "hello testAroundAOP";
	}
}
