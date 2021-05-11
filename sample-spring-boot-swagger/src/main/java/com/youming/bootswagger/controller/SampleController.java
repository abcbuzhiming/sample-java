package com.youming.bootswagger.controller;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "示例控制器" })
@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@ApiOperation(value = "hello", notes = "看服务器是否正常工作", httpMethod = "GET")
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "hello sample-spring-boot-swagger";
	}

	/**
	 * 演示最常见的一种方式，注意这种方式的参数是附加在url地址后以queryString方式传递的
	 */
	@ApiOperation(value = "queryString方式传递数据", notes = "这种方式的参数是附加在url地址后以queryString方式传递的", httpMethod = "GET")
	@ResponseBody
	@RequestMapping(value = "/query", produces = "application/json")
	public String query(@ApiParam(value = "用户名称", required = false) @RequestParam(value = "name") String name) {
		logger.info("param:" + name);
		return "sample request by query param ";
	}

	/**
	 * rest风格的path传参，注意@PathVariable的注解是必须的
	 */
	@ApiOperation(value = "path方式传递数据", notes = "REST URL，参数在Url中", httpMethod = "GET")
	@ResponseBody
	@RequestMapping(value = "/path/{name}/{password}", produces = "application/json")
	public String path(@ApiParam(value = "用户名称", required = true) @PathVariable(value = "name") String name,
			@ApiParam(value = "用户密码", required = true) @PathVariable(value = "password") String password) {
		logger.info("param:" + name + "|" + password);
		return "sample request by path param ";
	}

	/**
	 * form方式传递数据
	 * 注意，以这种方式时，必须在@RequestMapping中添加headers参数，设置content-type为application/x-www-form-urlencoded(表单提交)或multipart/form-data(上传文件)
	 */
	@ApiOperation(value = "form方式传递数据", notes = "演示以form方式传递数据，这种方式允许传递长度比较大的数据")
	/*
	 * @ApiImplicitParams({
	 * 
	 * @ApiImplicitParam(name = "name",value = "用户名称",paramType = "form",dataType =
	 * "string"),
	 * 
	 * @ApiImplicitParam(name = "password",value = "用户密码",paramType =
	 * "form",dataType = "string") })
	 */
	@RequestMapping(value = "/form", method = RequestMethod.POST, produces = "application/json", headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public String form(@ApiParam(value = "用户名称", required = true) @RequestParam(value = "name") String name,
			@ApiParam(value = "用户密码", required = true) @RequestParam(value = "password") String password) {
		logger.info("param:" + name + "|" + password);
		return "sample request by form param ";
	}

}
