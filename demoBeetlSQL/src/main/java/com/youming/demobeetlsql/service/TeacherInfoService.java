package com.youming.demobeetlsql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youming.demobeetlsql.mapper.TeacherInfoMapper;
import com.youming.demobeetlsql.model.po.TeacherInfo;


@Service
public class TeacherInfoService {

	@Autowired
	private TeacherInfoMapper teacherInfoMapper;
	
	
	
	/**
	 * 根据userName获取对象
	 * */
	public TeacherInfo getTeacherInfoByUserName(String userName){
		TeacherInfo teacherInfo = new TeacherInfo();
		teacherInfo.setUserName(userName);
		teacherInfo =  this.teacherInfoMapper.templateOne(teacherInfo);
		return teacherInfo;
	}
}
