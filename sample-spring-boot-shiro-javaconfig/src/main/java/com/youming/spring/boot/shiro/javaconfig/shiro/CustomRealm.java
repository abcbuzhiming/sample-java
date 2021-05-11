package com.youming.spring.boot.shiro.javaconfig.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.youming.spring.boot.shiro.javaconfig.dao.ShiroSampleDao;
import com.youming.spring.boot.shiro.javaconfig.utils.SpringUtils;

import java.util.Set;

/**
 * @author youming
 */
public class CustomRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(CustomRealm.class);

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		logger.info("CustomRealm调用认证：" + this.hashCode());
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String username = token.getUsername();
		ShiroSampleDao shiroSampleDao = SpringUtils.getBean(ShiroSampleDao.class);
		String password = shiroSampleDao.getPasswordByUsername(username);
		String className = this.getName();
		logger.info("className:" + className);
		//注意下面最后一个参数，如果用this.getName,那么你必须记住这个值，然后才能正确删除
		return new SimpleAuthenticationInfo(username, password, "111");
	}

	/**
	 * 授权 如果不使用缓存，则每次判断授权都会调用这个方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		logger.info("CustomRealm调用授权：" + this.hashCode());
		String username = (String) super.getAvailablePrincipal(principalCollection);
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		ShiroSampleDao shiroSampleDao = SpringUtils.getBean(ShiroSampleDao.class);
		Set<String> roles = shiroSampleDao.getRolesByUsername(username);
		authorizationInfo.setRoles(roles);
		roles.forEach(role -> {
			Set<String> permissions = shiroSampleDao.getPermissionsByRole(role);
			authorizationInfo.addStringPermissions(permissions);
		});
		return authorizationInfo;
	}

	/**
	 * 清除特定用户的cache的范例
	 * http://shiro-user.582556.n2.nabble.com/Clearing-cached-permissions-for-a-different-user-td7578699.html
	 * 清除权限缓存 使用方法：在需要清除用户权限的地方注入 ShiroRealm, 然后调用其clearCache方法。 (已知问题，不能和spring
	 * boot的开发热重启一起使用，一旦重启，缓存清不掉了,只在开发中存在的问题，考虑以后不用父类的，用本地realm)
	 */
	public void clearCacheFor(String id) {
		logger.info("CustomRealm清除Cache：" + this.hashCode());
		String className = this.getName();		//这个getName每次热加载，值会变
		logger.info("className:" + className);	
		//注意第二个参数必须和生成时的一模一样
		PrincipalCollection principalsNew = new SimplePrincipalCollection(id, "111");		
		PrincipalCollection principals = (SimplePrincipalCollection) SecurityUtils.getSubject().getPrincipals();
		//System.out.println(principalsNew.asSet() + "|" + principals.asSet() + "|"
		//		+ principalsNew.asSet().equals(principals.asSet()));
		
		System.out.println(principalsNew.hashCode() + "|" + principals.hashCode());
		System.out.println(principalsNew.equals(principals)); 

		Cache<Object, AuthorizationInfo> cache = super.getAuthorizationCache();
		System.out.println("AuthorizationInfo集合：" + cache.keys() + "|" + cache.values());
		// cache.clear();
		super.clearCache(principalsNew); // 所有的缓存都删除，包括验证信息和授权信息
		// super.clearCachedAuthorizationInfo(principals); //只删除授权信息 

	}
}
