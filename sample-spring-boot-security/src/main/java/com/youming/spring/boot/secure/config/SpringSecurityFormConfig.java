package com.youming.spring.boot.secure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 这是spring Security官方给出的默认配置 它实际是一个基于form表单的验证配置
 * https://docs.spring.io/spring-security/site/docs/5.0.11.RELEASE/guides/html5/hellomvc-javaconfig.html
 * https://docs.spring.io/spring-security/site/docs/5.0.11.RELEASE/reference/htmlsingle/#jc-httpsecurity
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityFormConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.authorizeRequests() // authorizeRequests（）方法有多个子节点，每个匹配器按其声明的顺序进行考虑。
				.antMatchers("/resources/**", "/signup", "/about", "/login.html*").permitAll() // 这些路径全部通过
				.antMatchers("/admin/**").hasRole("ADMIN") // 这些路径需要admin角色
				.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')") // 这些路径需要同时ADMIN和DBA角色
				.anyRequest().authenticated() // 任何请求，需要已登录身份

				.and() // 附加新策略
				.formLogin() // 建立form验证
				// .usernameParameter("username") // 自定义登录请求用的账号，默认是username
				// .passwordParameter("password") // 自定义登录请求用的账号，默认是username
				.loginPage("/login.html") // 自定义登录页地址，所有未登录请求会跳转到这个页面，默认是/login,你如果重定义了这个url，需要给这个地址加上permitAll否则会被拦截
				.loginProcessingUrl("/doLogin") // 自定义登录请求的url地址，默认是/login,你如果重定义了这个url，需要给这个地址加上permitAll否则会被拦截
				.defaultSuccessUrl("/index.html") // 自定义登录成功后跳转的页面，默认是/
				.failureUrl("/login.html?error") // 用户登录失败时跳转的url，默认是/login?error

				.and() // 附加新策略
				.logout() // 定义logout
				.logoutUrl("/doLogout") // 定义logout的url，默认是/logout
				.logoutSuccessUrl("/login") // 登出后跳转的url，默认是/login?logout
				// .logoutSuccessHandler(logoutSuccessHandler) //登出成功后进行处理的处理器
				.invalidateHttpSession(true) // 注销时让session失效
				// .addLogoutHandler(logoutHandler) //新增登出前调用处理器，可用于日志
				// .deleteCookies(cookieNamesToClear) //删除特定的cookie

				//.and()// 附加新策略
				//.accessDeniedHandler(customAccessDeniedHandler)		// 已登入用户的权限错误
                //.authenticationEntryPoint(customAccessDeniedHandler);// 未登入用户的权限错误
		
				//.and()// 附加新策略
				//.addFilterAt(filter, atFilter)	// 替换原先的表单登入 Filter	
				
				.and()// 附加新策略
				.csrf().disable(); // 关闭csrf，默认情况是开着的，会往页面写入隐藏的input输入token以防止csrf

				
	}

	/**
	 * 获取AuthenticationManager
	 * */
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// 基于内存的用户存储和认证，认证成功后，给予USER角色(5.x以后版本需要passwordEncoder编码)
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("admin")
				.password(new BCryptPasswordEncoder().encode("123456")).roles("USER");

	}
}
