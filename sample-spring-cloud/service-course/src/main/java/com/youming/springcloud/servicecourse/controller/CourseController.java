package com.youming.springcloud.servicecourse.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.youming.springcloud.servicecourse.utils.JsonTransformUtils;

@RestController
@RequestMapping("/course")
public class CourseController {

	@ResponseBody
	@RequestMapping("/getAll")
	public List<Map<String, Object>> getAll() {
		
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Map<String, Object> course = new HashMap<String, Object>();
			course.put("courseName", RandomStringUtils.random(12, true, false));
			course.put("courseNum", Integer.valueOf(RandomStringUtils.random(6, false, true)));
			list.add(course);
		}
		return list;
	}
}
