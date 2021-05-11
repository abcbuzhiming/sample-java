package com.youming.demoshiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
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
import org.springframework.web.filter.DelegatingFilterProxy;


import com.youming.demoshiro.shiro.realm.StatelessRealm;
import com.youming.demoshiro.shiro.realm.UserRealm;
import com.youming.demoshiro.shiro.web.filter.stateless.StatelessAuthenticationFilter;
import com.youming.demoshiro.utils.SpringUtils;

/**
 * 无状态shiro配置 服务器不保存session，基于JWT(json web token)，从JWT中直接反向出数据
 * 核心思路，不使用Shiro的Session和remember Me cookie管理
 * 添加无状态拦截器，所有需要权限的链接，首先通过拦截器进行登录，登录时会从jwt中得到数据，并使用subject.login
 */
public class ShiroStatelessConfig {
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
	 * session管理器 参考：http://shiro.apache.org/session-management.html
	 * http://shiro.apache.org/web.html#session-management
	 * shiro的session有几种，非web环境下的DefaultSessionManager；web环境下基于servlet容器的ServletContainerSessionManager；和自定义原生Web
	 * Session的DefaultWebSessionManager
	 * ServletContainerSessionManager产生的session和servlet
	 * session一致，由容器配置来管理，DefaultWebSessionManager则完全由自己定义
	 */
	@Bean(name = "sessionManager")
	public SessionManager sessionManager() {
		DefaultSessionManager sessionManager = new DefaultSessionManager();
		sessionManager.setSessionValidationSchedulerEnabled(false); // 不校验session是否过期
		return sessionManager;
	}

	
	/**
	 * 注册DelegatingFilterProxy(Shiro) 让ServletContainer管理Filter的生命周期
	 * 没有这一步，将导致Spring生成的filter不能访问Shiro的对象，这两者不处于同一上下文
	 */
	@Bean
	public FilterRegistrationBean delegatingFilterProxy() {
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
		StatelessRealm statelessRealm = springUtils.getApplicationContext().getBean(StatelessRealm.class);
		securityManager.setRealm(statelessRealm);
		// securityManager.setCacheManager(cacheManager); //设置缓存管理器
		securityManager.setSessionManager(this.sessionManager()); // 设置session管理器
		// securityManager.setRememberMeManager(rememberMeManager);		//不设置remember功能

		DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
		sessionStorageEvaluator.setSessionStorageEnabled(false); // 不存储session
		DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
		defaultSubjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
		securityManager.setSubjectDAO(defaultSubjectDAO);
		return securityManager;
	}

	/**
	 * shiroFilter，用bean方式添加的话，自动注册默认是会拦截/*,也就是所有的地址
	 */
	@Bean(name = "shiroFilterFactoryBean")
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		// System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		shiroFilterFactoryBean.setSecurityManager(this.securityManager());
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/static/**", "anon"); // 不拦截静态资源
		filterChainDefinitionMap.put("/**","stateless");		//拦截其它所有路径，注意顺序不能错，不能放在anon拦截器前面
		filterChainDefinitionMap.put("/user/testRoleFilter", "roles[user,guest]"); // 要求同时拥有user,guest身份
		filterChainDefinitionMap.put("/user/testPermissionFilter", "perms[user:info:read,user:info:insert]"); // 要求同时拥有user,guest身份
		// shiroFilterFactoryBean.setUnauthorizedUrl("/403"); // 未授权界面;
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		StatelessAuthenticationFilter statelessAuthenticationFilter = springUtils.getApplicationContext().getBean(StatelessAuthenticationFilter.class);
		shiroFilterFactoryBean.getFilters().put("stateless",statelessAuthenticationFilter);	//注册自定义stateless拦截器
		return shiroFilterFactoryBean;
	}
}
