package com.youming.demospringsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.youming.demospringsecurity.security.core.userdetails.LoginSecurityUserDetailsService;
import com.youming.demospringsecurity.utils.SpringUtils;

/**
 * 基础Spring Security配置
 * 注意@EnableWebSecurity和@EnableGlobalMethodSecurity在一个应用里只能有一个存在，在使用别的配置的时候，得注释这两个
 */
// @Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) // 使用基于表达式的语法
public class SecurityBaseConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SpringUtils springUtils;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* antMatchers是按添加的顺序匹配的，第一个规则被匹配到，就不会匹配后面的 */
		http.authorizeRequests().antMatchers("/", "/index", "/static/**").permitAll(); // 允许这些路径被所有人访问

		/**
		 * 研究表明，基本配置产生的效果非常死板：
		 * 要求任何请求地址都必须通过登录验证，无登录则自动跳转到login地址(无法关闭跳转，也就是说无法使用restful风格),login和logout的地址是允许无验证访问的
		 * loginPage定义的登录页的地址,如果没有身份验证访问其它页面，则会自动跳转
		 * security会自动往表单页添加一个hidden的input，添加了_csrf.token和_csrf.token作为房子csrf攻击的令牌
		 * 在登录时，默认访问地址仍然是这个login，然后会被spring
		 * security的拦截器会拦截登录请求地址loginProcessingUrl（在spring
		 * mvc的控制器之前），默认这个地址loginPage相同，如果人为修改form提交地址不为login，会发生302错误
		 */

		http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login")
				.loginProcessingUrl("/doLogin").permitAll().and().logout().permitAll();

		/**
		 * logoutUrl,定义的登出的访问地址，如果开启csrf，则必须是post模式
		 */
		http.logout().logoutUrl("/doLogout").logoutSuccessUrl("/index")
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
