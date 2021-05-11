package com.youming.demoshiro.shiro.authc;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;


public class StatelessToken extends UsernamePasswordToken{

	private String jsession;
	private String host;
	
	public StatelessToken(String jsession) {
		this.jsession = jsession;
	}
	
	public String getJsession() {
		return this.jsession;
	}

}
