package com.youming.demoshiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.youming.demoshiro.shiro.realm.UserRealm;
import com.youming.demoshiro.utils.SpringUtils;



/**
 * shiro将session和cookie保存在redis中的配置，服务器本身无状态，状态在redis中
 * */
//@Configuration		不能提前configure，必须从主configuration中导入
public class ShiroBaseConfig {

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
	 * 需要注意的是，shiro的rememberMe cookie是无法从服务端删除的(服务端只能通知浏览器删除，但是实际删除后这个cookie仍然有效)，
	 * 如果有人获得了这个cookie，那么它可以以这个cookie访问系统，因此，该cookie里不应该存放关键信息
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
		UserRealm userRealm = this.springUtils.getApplicationContext().getBean(UserRealm.class);
		securityManager.setRealm(userRealm);		//只有一个Realm时的配置 
 		securityManager.setSessionManager(this.sessionManager()); //设置session管理器
		securityManager.setRememberMeManager(this.rememberMeManager());		//设置remember管理器(另外一种cookie)
		return securityManager;
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
