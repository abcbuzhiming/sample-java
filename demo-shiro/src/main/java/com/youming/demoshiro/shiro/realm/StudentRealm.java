package com.youming.demoshiro.shiro.realm;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StudentRealm extends AuthorizingRealm {

	// 日志生成器
	private static final Logger logger = LoggerFactory.getLogger(StudentRealm.class);

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		logger.info("授权调用Student：" + principals.toString());
		@SuppressWarnings("unchecked")
		Map<String, String> principalMap = (HashMap<String, String>) principals.getPrimaryPrincipal(); // 获取当前用户的信息
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		/**
		 * 这里可以通过查数据库表获取用户的身份(Role)和权限(pemssion)
		 */
		
		simpleAuthorizationInfo.addRole("student");

		simpleAuthorizationInfo.addStringPermission("student:info:read");
		simpleAuthorizationInfo.addStringPermission("student:info:insert"); // 添加3个权限
		simpleAuthorizationInfo.addStringPermission("student:info:update");

		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		logger.info("认证调用Student");
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String userName = usernamePasswordToken.getUsername();
		char[] password = usernamePasswordToken.getPassword();

		/**
		 * 这里可以访问数据库查询用户密码的正确性
		 */
		Map<String, String> principalMap = new HashMap<String, String>();
		principalMap.put("userType", "student");
		principalMap.put("username", userName);
		principalMap.put("loginTime", String.valueOf(System.currentTimeMillis()));
		Object credentials = password; // 密码
		// （3）realmName：当前realm对象的name，调用父类的getName()方法即可
		String realmName = this.getName();
		// 每个Realm会有一个CredentialsMatcher，这个Matcher的默认实现会对比你输入的密码和你填入SimpleAuthenticationInfo的密码，来决定是否正确
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principalMap, credentials, realmName);
		return info;
	}

}
