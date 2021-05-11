package com.youming.spring.boot.api.version.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youming.spring.boot.api.version.annotation.ApiVersion;

import io.swagger.annotations.ApiOperation;

@ApiVersion("1_1_0")
@Controller
@RequestMapping("/{version}/sample")
public class Sample1_1Controller {

	//@ApiOperation(value = "hello", notes = "hello v1_1_0",httpMethod= "GET")
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "hello 1.1.0";
	}
}
