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
public class SecurityCustomConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SpringUtils springUtils;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* antMatchers是按添加的顺序匹配的，第一个规则被匹配到，就不会匹配后面的 */
		http.authorizeRequests().antMatchers("/", "/static/**").permitAll(); // 允许这些路径被所有人访问

		/*
		 * http.authorizeRequests().antMatchers("/").permitAll().anyRequest().
		 * authenticated().and().formLogin()
		 * .loginPage("/login").permitAll().and().logout().permitAll();
		 * //要求任何请求地址都必须通过登录验证，无登录则自动跳转到login地址
		 */

		// 必须拥有角色ADMIN
		// http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// 基于内存的用户存储和认证
		// auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");

		// 基于数据库的用户存储、认证,需要提供一个DataSource作为中介
		/*
		 * auth.jdbcAuthentication().dataSource(dataSource)
		 * .usersByUsernameQuery("select account,password,true from user where account=?"
		 * )
		 * .authoritiesByUsernameQuery("select account,role from user where account=?");
		 */
		// 设置UserDetailsService用于获取用户数据
		UserDetailsService userDetailsService = this.springUtils.getApplicationContext()
				.getBean(LoginSecurityUserDetailsService.class);
		auth.userDetailsService(userDetailsService);
		/**
		 * 该方法可添加多个AuthenticationProvider,但是如果要定义多个AuthenticationProvider的执行策略，则需要自己实现AuthenticationManager接口
		 * 这个接口的默认实现是ProviderManager，其中authenticate(Authentication)方法即是使用AuthenticationProvider的方法，默认是只要有一个成功就行
		 */
		 //auth.authenticationProvider(new AuthenticationProvider());
	}

	/**
	 * 自定义的AuthenticationManager
	 * 仅在AuthenticationManagerBuilder未被填充时才有效
	 * */
	/*
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		
		return null;
	}
	*/
}
