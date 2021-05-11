package com.youming.demobeetlsql.model.po;

import java.util.Date;

public class VerificationCode {
	private Long id ;
	//手机验证码
	private String code ;
	//当前访问的IP地址
	private String ip ;
	//手机号码
	private String mobilePhone ;
	//手机验证码产生时间
	private Date createTime ;
	
	public VerificationCode() {
	}
	
	public Long getId(){
		return  id;
	}
	public void setId(Long id ){
		this.id = id;
	}
	
	public String getCode(){
		return  code;
	}
	public void setCode(String code ){
		this.code = code;
	}
	
	public String getIp(){
		return  ip;
	}
	public void setIp(String ip ){
		this.ip = ip;
	}
	
	public String getMobilePhone(){
		return  mobilePhone;
	}
	public void setMobilePhone(String mobilePhone ){
		this.mobilePhone = mobilePhone;
	}
	
	public Date getCreateTime(){
		return  createTime;
	}
	public void setCreateTime(Date createTime ){
		this.createTime = createTime;
	}
}
