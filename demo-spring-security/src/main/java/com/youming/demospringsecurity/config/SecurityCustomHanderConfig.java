package com.youming.demospringsecurity.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.youming.demospringsecurity.security.web.RestAuthenticationEntryPoint;
import com.youming.demospringsecurity.security.web.access.AccessDeniedHandlerImpl;
import com.youming.demospringsecurity.security.web.authentication.MySimpleUrlAuthenticationSuccessHandler;

/**
 * Spring Security配置,修改处理器，即登录异常时的系统处理行为，登录成功或失败时系统的处理行为
 * 注意@EnableWebSecurity和@EnableGlobalMethodSecurity在一个应用里只能有一个存在，在使用别的配置的时候，得注释这两个
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 使用基于表达式的语法
public class SecurityCustomHanderConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
	private MySimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private AccessDeniedHandlerImpl accessDeniedHandlerImpl;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* antMatchers是按添加的顺序匹配的，第一个规则被匹配到，就不会匹配后面的 */
		http.authorizeRequests().antMatchers("/", "/index", "/static/**").permitAll(); // 允许这些路径被所有人访问

		/*
		 * 参考：14.2 ExceptionTranslationFilter
		 * https://docs.spring.io/spring-security/site/docs/4.2.4.RELEASE/reference/
		 * htmlsingle/#exception-translation-filter authenticationEntryPoint
		 * 未通过验证过的请求，由它处理； 
		 * accessDeniedHandler 登录验证通过过，如果访问到了当前用户权限不够访问的资源，由它处理行为；注意这里有个坑，如果在spring mvc方法上加注解，这个注解引发的异常会被spring mvc advice拦截而不会被accessDeniedHandler处理
		 * successHandler 登录成功后的请求，由它处理； failureHandler 登录失败的请求，由它处理；
		 */
		
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(this.restAuthenticationEntryPoint)
				.accessDeniedHandler(this.accessDeniedHandlerImpl).and().authorizeRequests().anyRequest()
				.authenticated().and().formLogin().successHandler(this.authenticationSuccessHandler)
				.failureHandler(new SimpleUrlAuthenticationFailureHandler()).loginPage("/login")
				.loginProcessingUrl("/doLogin").permitAll();

		/**
		 * logoutUrl,定义的登出的访问地址，如果开启csrf，则必须是post模式
		 */
		http.logout().permitAll().logoutUrl("/doLogout").logoutSuccessUrl("/index")
				// .logoutSuccessHandler(logoutSuccessHandler)
				.invalidateHttpSession(true);
		// .addLogoutHandler(logoutHandler)
		// .deleteCookies(cookieNamesToClear) //删除特定名称的cookie

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// 基于内存的用户存储和认证
		auth.inMemoryAuthentication().withUser("user").password("123456").roles("USER");

	}
}
