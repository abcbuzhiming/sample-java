package com.youming.demoshiro.shiro.realm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.youming.demoshiro.shiro.authc.StatelessToken;

@Component
public class StatelessRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(StatelessRealm.class);

	

	/**
	 * 授权
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		Map<String, String> principalMap = (HashMap<String, String>) principalCollection.getPrimaryPrincipal(); // 获取当前用户的信息

		/**
		 * 从redis中读取授权信息
		 * */
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRole("user");
		simpleAuthorizationInfo.addStringPermission("user:info:read");
		return simpleAuthorizationInfo;
	}

	/**
	 * 登录验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		// 1. 把AuthenticationToken转换为StatelessToken
		StatelessToken statelessToken = (StatelessToken) authenticationToken;
		String jsession = statelessToken.getJsession();
		/**
		 * json web token反推出具体数据
		 * */
		String username = "username";
		Map<String,String> principalMap = new HashMap<String,String>();
		principalMap.put("username", username);
		principalMap.put("loginTime",String.valueOf(System.currentTimeMillis()));
		Object credentials = "password"; // 密码
		// （3）realmName：当前realm对象的name，调用父类的getName()方法即可
		String realmName = this.getName();
		//每个Realm会有一个CredentialsMatcher，这个Matcher的默认实现会对比你输入的密码和你填入SimpleAuthenticationInfo的密码，来决定是否正确
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principalMap, credentials, realmName);
		return info;
	}

}
