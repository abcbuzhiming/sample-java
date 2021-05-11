package com.youming.demoshiro.config;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.youming.demoshiro.shiro.realm.AdminRealm;
import com.youming.demoshiro.shiro.realm.StudentRealm;
import com.youming.demoshiro.shiro.realm.TeacherRealm;
import com.youming.demoshiro.shiro.realm.UserRealm;
import com.youming.demoshiro.utils.SpringUtils;

/**
 * shiro配置：多个Realm时的认证和授权
 * */
public class ShiroMultipleRealmConfig {
	@Autowired
	private SpringUtils springUtils;
	
	/**
	 * 使用shiro注解
	 */
	@Bean(name = "authorizationAttributeSourceAdvisor")
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(this.securityManager());
		return authorizationAttributeSourceAdvisor;
	}
	
	/**
	 * session管理器
	 * 参考：http://shiro.apache.org/session-management.html http://shiro.apache.org/web.html#session-management
	 * shiro的session有几种，非web环境下的DefaultSessionManager；web环境下基于servlet容器的ServletContainerSessionManager；和自定义原生Web Session的DefaultWebSessionManager
	 * ServletContainerSessionManager产生的session和servlet session一致，由容器配置来管理，DefaultWebSessionManager则完全由自己定义
	 * */
	@Bean(name = "sessionManager")
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionValidationInterval(60000);		//相隔多久检查一次session的有效性（毫秒单位）,不配置是60分钟
		sessionManager.setGlobalSessionTimeout(1800000);		//全局session最长闲置时间(毫秒单位)
		sessionManager.setSessionValidationSchedulerEnabled(true);		//是否开启检查session有效任务,默认开启
		SimpleCookie simpleCookie = new SimpleCookie("JSESSION");		//设置session的key的名称
		simpleCookie.setHttpOnly(true);		//cookie为httponly(浏览器js无法读取)
		simpleCookie.setMaxAge(604800);		//cookie过期时间，(单位秒,-1永不过期)
		sessionManager.setSessionIdCookie(simpleCookie);		//设置session模板
		//SessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();		//默认会自己建立一个任务调度器
		//sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
		sessionManager.setDeleteInvalidSessions(true);	//删除无效的session

		
		return sessionManager;
	}
	
	
	/**
	 * rememberMe功能cookie管理器
	 * */
	@Bean(name = "rememberMeManager")
	public RememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCipherKey(org.apache.shiro.codec.Base64.decode("34TX5Gvgvlk3VP0GmHSy7Q=="));		//cookie加密用密钥
		SimpleCookie simpleCookie = new SimpleCookie("JCOOKIE");		//设置session的key的名称
		simpleCookie.setHttpOnly(true);		//cookie为httponly(浏览器js无法读取)
		simpleCookie.setMaxAge(604800);		//cookie过期时间，(单位秒,-1永不过期)
		cookieRememberMeManager.setCookie(simpleCookie);
		return cookieRememberMeManager;
	}
	
	
	/**
	 * 注册DelegatingFilterProxy(Shiro)
	 * 让ServletContainer管理Filter的生命周期
	 * 没有这一步，将导致Spring生成的filter不能访问Shiro的对象，这两者不处于同一上下文
	 * */
	@Bean
	public FilterRegistrationBean delegatingFilterProxy(){
	    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
	    DelegatingFilterProxy proxy = new DelegatingFilterProxy();
	    proxy.setTargetFilterLifecycle(true);
	    proxy.setTargetBeanName("shiroFilterFactoryBean");
	    filterRegistrationBean.setFilter(proxy);
	    return filterRegistrationBean;
	}
	
	/**
	 * 安全管理器，shiro的核心
	 */
	@Bean(name = "securityManager")
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		/*
		StudentRealm studentRealm = this.springUtils.getApplicationContext().getBean(StudentRealm.class);
		TeacherRealm teacherRealm = this.springUtils.getApplicationContext().getBean(TeacherRealm.class);
		AdminRealm adminRealm = this.springUtils.getApplicationContext().getBean(AdminRealm.class);
		List<Realm> realms = new ArrayList<Realm>();
		realms.add(studentRealm);
		realms.add(teacherRealm);
		realms.add(adminRealm);
		securityManager.setRealms(realms);		//多个realms都加入
		*/
 		securityManager.setSessionManager(this.sessionManager()); //设置session管理器
		securityManager.setRememberMeManager(this.rememberMeManager());		//设置remember管理器(另外一种cookie)
		securityManager.setAuthenticator(this.authenticator());		//设置多个realm时的验证策略器
		securityManager.setAuthorizer(this.authorizer());		//设置多个realm的授权策略
		return securityManager;
	}
	
	/**
	 * 自定义认证器，可以实现多Realm认证，并且可以指定特定Realm处理特定类型的验证
	 * 参考：http://shiro.apache.org/authentication.html#Authentication-%7B%7BAuthenticationStrategy%7D%7D
	 * */
	@Bean(name = "authenticator")
	public Authenticator authenticator() {
		ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();		//这个认证器可以自定义扩展
		StudentRealm studentRealm = this.springUtils.getApplicationContext().getBean(StudentRealm.class);
		TeacherRealm teacherRealm = this.springUtils.getApplicationContext().getBean(TeacherRealm.class);
		AdminRealm adminRealm = this.springUtils.getApplicationContext().getBean(AdminRealm.class);
		List<Realm> realms = new ArrayList<Realm>();
		realms.add(studentRealm);
		realms.add(teacherRealm);
		realms.add(adminRealm);
		modularRealmAuthenticator.setRealms(realms);
		//modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());		//默认策略，如果一个（或多个）领域认证成功，则认为整体尝试成功，并返回所有成功的验证。 如果没有任何验证成功，则尝试失败。
		modularRealmAuthenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());		//仅使用从第一个成功验证的Realm返回的信息。 所有进一步的认证将被忽略。 如果没有任何验证成功，则尝试失败。
		//modularRealmAuthenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());		//所有配置的Realm都必须成功进行身份验证才能成功进行整体尝试。 如果任何一个人未成功认证，则尝试失败。
		return modularRealmAuthenticator;
	}
	
	/**
	 * 自定义授权器，自定义授权时的顺序
	 * 参考：http://shiro.apache.org/authorization.html#Authorization-AuthorizationSequence
	 * */
	@Bean(name = "authorizer")
	public Authorizer authorizer() {
		ModularRealmAuthorizer modularRealmAuthorizer = new ModularRealmAuthorizer();		//授权检查器，可自定义授权器检查策略
		StudentRealm studentRealm = this.springUtils.getApplicationContext().getBean(StudentRealm.class);
		TeacherRealm teacherRealm = this.springUtils.getApplicationContext().getBean(TeacherRealm.class);
		AdminRealm adminRealm = this.springUtils.getApplicationContext().getBean(AdminRealm.class);
		List<Realm> realms = new ArrayList<Realm>();
		realms.add(studentRealm);
		realms.add(teacherRealm);
		realms.add(adminRealm);
		modularRealmAuthorizer.setRealms(realms);
		return modularRealmAuthorizer;
	}
	
	/**
	 * shiroFilter，用bean方式添加的话，自动注册默认是会拦截/*,也就是所有的地址
	 */
	@Bean(name = "shiroFilterFactoryBean")
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		//System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		
		shiroFilterFactoryBean.setSecurityManager(this.securityManager());
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/static/**", "anon");		//不拦截静态资源
		filterChainDefinitionMap.put("/user/testRoleFilter", "roles[user,guest]");		//要求同时拥有user,guest身份
		filterChainDefinitionMap.put("/user/testPermissionFilter", "perms[user:info:read,user:info:insert]");		//要求同时拥有user,guest身份
		//shiroFilterFactoryBean.setUnauthorizedUrl("/403");		// 未授权界面;
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return shiroFilterFactoryBean;
	}
	
	
}
