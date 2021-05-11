package com.youming.spring.boot.shiro.javaconfig.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.youming.spring.boot.shiro.javaconfig.shiro.CustomRealm;

import net.sf.ehcache.CacheManager;

/**
 * shiro配置
 */
@Configuration
public class ShiroConfig {

	/**
	 * shiro 生命周期处理器
	 * */
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		System.out.println("lifecycleBeanPostProcessor初始化：");
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * DefaultAdvisorAutoProxyCreator 和 AuthorizationAttributeSourceAdvisor 用于开启
	 * shiro 注解的使用 如 @RequiresAuthentication， @RequiresUser， @RequiresPermissions 等
	 *
	 * @return DefaultAdvisorAutoProxyCreator
	 */

	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		System.out.println("advisorAutoProxyCreator初始化：");
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	/**
	 * 使用shiro注解
	 */
	@Bean(name = "authorizationAttributeSourceAdvisor")
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		System.out.println("authorizationAttributeSourceAdvisor初始化：");
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
		System.out.println("sessionManager初始化：");
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionValidationInterval(60000); // 相隔多久检查一次session的有效性（毫秒单位）,不配置是60分钟
		sessionManager.setGlobalSessionTimeout(1800000); // 全局 session最长闲置时间(毫秒单位)
		sessionManager.setSessionValidationSchedulerEnabled(true); // 是否开启检查session有效任务,默认开启
		SimpleCookie simpleCookie = new SimpleCookie("JSESSION"); // 设置session的key的名称
		simpleCookie.setHttpOnly(true); // cookie为httponly(浏览器js无法读取)

		simpleCookie.setMaxAge(604800); // cookie过期时间，(单位秒,-1永不过期)
		sessionManager.setSessionIdCookie(simpleCookie); // 设置session模板
		// SessionValidationScheduler sessionValidationScheduler = new
		// ExecutorServiceSessionValidationScheduler(); //默认会自己建立一个任务调度器
		// sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
		sessionManager.setDeleteInvalidSessions(true); // 删除无效的session

		return sessionManager;
	}
 
	/**
	 * rememberMe功能cookie管理器
	 */
	@Bean(name = "rememberMeManager")
	public RememberMeManager rememberMeManager() {
		System.out.println("rememberMeManager初始化：");
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCipherKey(org.apache.shiro.codec.Base64.decode("36TX5Gvgvlk5VP0GmHSy7Q==")); // cookie加密用密钥
		SimpleCookie simpleCookie = new SimpleCookie("JCOOKIE"); // 设置session的key的名称
		simpleCookie.setHttpOnly(true); // cookie为httponly(浏览器js无法读取)
		simpleCookie.setMaxAge(604800); // cookie过期时间，(单位秒,-1永不过期)
		cookieRememberMeManager.setCookie(simpleCookie);
		return cookieRememberMeManager;
	}

	/**
	 * 注册DelegatingFilterProxy(Shiro) 让ServletContainer管理Filter的生命周期
	 * 没有这一步，将导致Spring生成的filter不能访问Shiro的对象，这两者不处于同一上下文
	 */
	@Bean
	public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
		System.out.println("delegatingFilterProxy初始化：");
		FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName("shiroFilterFactoryBean");
		filterRegistrationBean.setFilter(proxy);
		return filterRegistrationBean;
	}

	/**
	 * 这里要注意：不要在Realm中注入其它Spring管理的类
	 * 因为这里初始化的比spring的自动扫描装配初始化早，会导致spring提前初始化注入类，注入类因为提前初始化，导致未能完成加强。比如service层失去事务能力
	 * 应该使用springUtils在使用前获取
	 */
	@Bean(name = "customRealm")
	public CustomRealm customRealm() {
		CustomRealm customRealm = new CustomRealm();
		System.out.println("customRealm初始化时的hashCode：" + customRealm.hashCode());
		//MemoryConstrainedCacheManager memoryConstrainedCacheManager = new MemoryConstrainedCacheManager();
		//customRealm.setCacheManager(memoryConstrainedCacheManager);		//设置Realm的缓存
		return customRealm;
	}

	/**
	 * 安全管理器，shiro的核心 
	 * 跟踪时发现清除的程序走的各个Realm上的Cache和CacheManage，而这上面的Cache都是空的，目前尚不清楚原因
	 */
	@Bean(name = "securityManager")
	public SecurityManager securityManager() {
		System.out.println("securityManager初始化：");
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(this.customRealm()); // 只有一个Realm时的配置

		MemoryConstrainedCacheManager memoryConstrainedCacheManager = new MemoryConstrainedCacheManager();
		EhCacheManager ehCacheManager = new EhCacheManager();
		// 将ehcacheManager转换成shiro包装后的ehcacheManager对象
		//CacheManager cacheManager = CacheManager.create();
		//ehCacheManager.setCacheManager(cacheManager);
		//securityManager.setCacheManager(ehCacheManager);
		securityManager.setCacheManager(memoryConstrainedCacheManager);
		securityManager.setSessionManager(this.sessionManager()); // 设置session管理器
		securityManager.setRememberMeManager(this.rememberMeManager()); // 设置remember管理器(另外一种cookie)

		return securityManager;
	}

	/**
	 * shiroFilter，用bean方式添加的话，自动注册默认是会拦截/*,也就是所有的地址
	 */
	@Bean(name = "shiroFilterFactoryBean")
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		System.out.println("shiroFilterFactoryBean初始化：");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		shiroFilterFactoryBean.setSecurityManager(this.securityManager());
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/static/**", "anon"); // 不拦截静态资源
		// shiroFilterFactoryBean.setUnauthorizedUrl("/403"); // 未授权界面;
		// shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}
}
