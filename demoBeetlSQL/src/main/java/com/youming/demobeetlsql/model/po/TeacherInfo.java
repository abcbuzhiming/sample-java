package com.youming.demobeetlsql.model.po;

import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

/*
* 
* gen by beetlsql 2017-06-15
*/
public class TeacherInfo   implements Serializable{
	private Long id ;
	//当前状态,是否接受问诊:1、接受;0、不接受
	private Integer acceptConsultationStatus ;
	//教授等级，目前分两级,1,讲师;2,教授。级别不同，提成和奖励的策略不同
	private Integer teachLevel ;
	//积分(解答问题获取)
	private BigDecimal accumulatePoints ;
	//最终学历
	private String education ;
	//毕业年份
	private String graduationDate ;
	//自我介绍
	private String introduceSelf ;
	//手机号码
	private String mobilePhone ;
	//昵称
	private String nickName ;
	//密码，SHA256加密后字符串(前后端逻辑代码强制要求12位以上,大小字母,数字，特殊字符混合字符串)
	private String password ;
	//可指导的学科和学段(待定,目前以json格式定义)
	private String professionalField ;
	//QQ OAuth2.0授权登录产生的openid
	private String qqOpenId ;
	//真实姓名
	private String trueName ;
	//用户名,8-15字符
	private String userName ;
	//微信 OAuth2.0授权登录产生的openid
	private String weixinOpenId ;
	//在职学校
	private String workingSchool ;
	
	public TeacherInfo() {
	}
	
	public Long getId(){
		return  id;
	}
	public void setId(Long id ){
		this.id = id;
	}
	
	public Integer getAcceptConsultationStatus(){
		return  acceptConsultationStatus;
	}
	public void setAcceptConsultationStatus(Integer acceptConsultationStatus ){
		this.acceptConsultationStatus = acceptConsultationStatus;
	}
	
	public Integer getTeachLevel(){
		return  teachLevel;
	}
	public void setTeachLevel(Integer teachLevel ){
		this.teachLevel = teachLevel;
	}
	
	public BigDecimal getAccumulatePoints(){
		return  accumulatePoints;
	}
	public void setAccumulatePoints(BigDecimal accumulatePoints ){
		this.accumulatePoints = accumulatePoints;
	}
	
	public String getEducation(){
		return  education;
	}
	public void setEducation(String education ){
		this.education = education;
	}
	
	public String getGraduationDate(){
		return  graduationDate;
	}
	public void setGraduationDate(String graduationDate ){
		this.graduationDate = graduationDate;
	}
	
	public String getIntroduceSelf(){
		return  introduceSelf;
	}
	public void setIntroduceSelf(String introduceSelf ){
		this.introduceSelf = introduceSelf;
	}
	
	public String getMobilePhone(){
		return  mobilePhone;
	}
	public void setMobilePhone(String mobilePhone ){
		this.mobilePhone = mobilePhone;
	}
	
	public String getNickName(){
		return  nickName;
	}
	public void setNickName(String nickName ){
		this.nickName = nickName;
	}
	
	public String getPassword(){
		return  password;
	}
	public void setPassword(String password ){
		this.password = password;
	}
	
	public String getProfessionalField(){
		return  professionalField;
	}
	public void setProfessionalField(String professionalField ){
		this.professionalField = professionalField;
	}
	
	public String getQqOpenId(){
		return  qqOpenId;
	}
	public void setQqOpenId(String qqOpenId ){
		this.qqOpenId = qqOpenId;
	}
	
	public String getTrueName(){
		return  trueName;
	}
	public void setTrueName(String trueName ){
		this.trueName = trueName;
	}
	
	public String getUserName(){
		return  userName;
	}
	public void setUserName(String userName ){
		this.userName = userName;
	}
	
	public String getWeixinOpenId(){
		return  weixinOpenId;
	}
	public void setWeixinOpenId(String weixinOpenId ){
		this.weixinOpenId = weixinOpenId;
	}
	
	public String getWorkingSchool(){
		return  workingSchool;
	}
	public void setWorkingSchool(String workingSchool ){
		this.workingSchool = workingSchool;
	}
	
	
	

}
