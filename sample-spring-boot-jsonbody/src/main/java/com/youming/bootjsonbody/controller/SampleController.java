package com.youming.bootjsonbody.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youming.bootjsonbody.annotation.RequestJson;
import com.youming.bootjsonbody.domain.User;
import com.youming.bootjsonbody.domain.UserInfo;
import com.youming.bootjsonbody.utils.JsonTransformUtils;

@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		logger.info("sample/hello " + System.currentTimeMillis());
		return "hello sample-spring-boot-jsonbody";
	}

	/**
	 * 方法1，把RequestBody设置到一个对象
	 */
	@ResponseBody
	@RequestMapping("/jsonbodyToObject")
	public String jsonbodyToObject(@RequestBody(required = false) UserInfo userInfo) {
		logger.info("sample/jsonbodyToObject： " + userInfo.getUsername() + "|" + userInfo.getPassword());
		return "OK";
	}

	/**
	 * 方法2，把RequestBody设置到一个map，map的值是object的
	 */
	@ResponseBody
	@RequestMapping("/jsonbodyToMap")
	public String jsonbodyToMap(@RequestBody(required = false) Map<String, Object> map) {
		logger.info("sample/jsonbodyToMap： " + map);
		return "OK";
	}

	
	@ResponseBody
	@RequestMapping("/jsonbodyToParam")
	public String jsonbodyToParam(@RequestJson(name="username")String username, @RequestJson String password,@RequestJson(name="obj")List<UserInfo> userinfo) {
		logger.info("sample/jsonbodyToParam： " + username + "|" + password + "|"+ JsonTransformUtils.toJson(userinfo));
		return "OK";
	}
}
