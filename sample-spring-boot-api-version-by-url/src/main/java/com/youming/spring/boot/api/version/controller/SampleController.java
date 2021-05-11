package com.youming.spring.boot.api.version.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youming.spring.boot.api.version.utils.SpringUtils;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("/hello") public String hello() { return
	 * "hello youming-sample-spring-boot-api-version"; }
	 */
	@ApiOperation(value = "hello", notes = "hello v1", httpMethod = "GET")
	@ResponseBody
	@RequestMapping(value = "/hello", params = "version=1")
	public String paramV1() {
		return "paramV1";
	}

	@ApiOperation(value = "hello", notes = "hello v2", httpMethod = "GET")
	@ResponseBody
	@RequestMapping(value = "/hello", params = "version=2")
	public String paramV2() {
		return "paramV2";
	}
	
	//通过URL Path实现版本控制
	@GetMapping("/v1/api/user")
	public int right1(){
	    return 1;
	}
	
	//通过QueryString中的version参数实现版本控制
	@GetMapping(value = "/api/user", params = "version=2")
	public int right2(@RequestParam("version") int version) {
	    return 2;
	}
	//通过请求头中的X-API-VERSION参数实现版本控制
	@GetMapping(value = "/api/user", headers = "X-API-VERSION=3")
	public int right3(@RequestHeader("X-API-VERSION") int version) {
	    return 3;
	}

}
