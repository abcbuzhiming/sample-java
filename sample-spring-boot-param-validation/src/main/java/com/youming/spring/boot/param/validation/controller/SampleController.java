package com.youming.spring.boot.param.validation.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.Spring;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youming.spring.boot.param.validation.utils.SpringUtils;


//@Validated必须放在类上面否则不起效
@Validated
@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "hello youming-sample-spring-boot-param-validation";
	}

	
	@ResponseBody
	@RequestMapping("/testLength")
	public String testLength(
			@RequestParam(value = "username") @Size(min = 7, max = 20, message = "用户名长度错误") String username) {
		return "ok";
	}

}
