package com.youming.spring.boot.api.version.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youming.spring.boot.api.version.annotation.ApiVersion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//@Api("SampleController V1.0.x")
@Controller
public class Sample1_0Controller {

	
	@ApiOperation(value = "hello", notes = "hello v1_0_0",httpMethod= "GET")
	@ApiVersion("1_0_0")
	@ResponseBody
	@RequestMapping("/{version}/sample/hello")
	public String hello() {
		return "hello 1.0.0";
	}
	
	@ApiOperation(value = "hello", notes = "hello v1_0_1",httpMethod= "GET")
	@ApiVersion("1_0_1")
	@ResponseBody
	@RequestMapping("/{version}/sample/hello")
	public String helloV1() {
		return "hello 1.0.1";
	}
	
	@ApiOperation(value = "hello", notes = "hello v1_0_2",httpMethod= "GET")
	@ApiVersion("1_0_2")
	@ResponseBody
	@RequestMapping("/{version}/sample/hello")
	public String helloV2() {
		return "hello 1.0.2";
	}
}
