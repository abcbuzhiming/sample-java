package com.youming.spring.boot.secure.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.youming.spring.boot.secure.security.authentication.CustomAuthenticationProvider;
import com.youming.spring.boot.secure.service.CustomUserDetailsService;
import com.youming.spring.boot.secure.web.authentication.CustomUsernamePasswordAuthenticationFilter;
import com.youming.spring.boot.secure.web.authentication.MyAuthenctiationFailureHandler;
import com.youming.spring.boot.secure.web.authentication.MyAuthenctiationSuccessHandler;

/**
 * 完全自定义登录流程，认证流程，鉴权流程，需要AuthenticationManager 如何注入AuthenticationManager
 * https://stackoverflow.com/questions/21633555/how-to-inject-authenticationmanager-using-java-configuration-in-a-custom-filter
 * 
 * 
 */
@Configuration
@EnableWebSecurity
//spring security默认不支持注解，需要这样打开
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true, proxyTargetClass = true)
public class SpringSecurityCustomConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;
	@Autowired
	MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

	// 5.x以后版本需要passwordEncoder编码
	// https://docs.spring.io/spring-security/site/docs/5.1.5.RELEASE/reference/htmlsingle/#jc-authentication-userdetailsservice
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 自定义认证者配置
	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider();
	}
	
	//配置封装ipAuthenticationToken的过滤器
	@Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() throws Exception {
		CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter = new CustomUsernamePasswordAuthenticationFilter();
        //为过滤器添加认证器
		AuthenticationManager authenticationManager = this.authenticationManager();
		customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager);
		//自定义登录成功处理
		customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenctiationSuccessHandler);
        //重写认证失败时的跳转页面
		customUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(myAuthenctiationFailureHandler);
        return customUsernamePasswordAuthenticationFilter;
    }

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests() // authorizeRequests（）方法有多个子节点，每个匹配器按其声明的顺序进行考虑。
				.antMatchers("/sample/hello", "/login.html*","/doLogin").permitAll() // 这些路径全部通过
				// .anyRequest().authenticated() // 任何请求，需要已登录身份

				.and() // 附加新策略
				.formLogin()
				// .usernameParameter("username") // 自定义登录请求用的账号，默认是username
				// .passwordParameter("password") // 自定义登录请求用的账号，默认是username
				.loginPage("/login.html") // 自定义登录页地址，所有未登录请求会跳转到这个页面，默认是/login,你如果重定义了这个url，需要给这个地址加上permitAll否则会被拦截
				.loginProcessingUrl("/doLogin") // 自定义登录请求的url地址，注意：如果你使用自定义的filter，则这个配置就无效了，登录url在filter里设置
				.defaultSuccessUrl("/index.html") // 自定义登录成功后跳转的页面，默认是/
				.failureUrl("/login.html?error") // 用户登录失败时跳转的url，默认是/login?error
				.successHandler(myAuthenctiationSuccessHandler) // 自定义登录成功处理，注意：如果你使用自定义的filter，则这个配置就无效了
				.failureHandler(myAuthenctiationFailureHandler) // 自定义登录失败处理，注意：如果你使用自定义的filter，则这个配置就无效了

				.and() // 附加新策略
				.logout() // 定义logout
				.logoutUrl("/doLogout") // 定义logout的url，默认是/logout
				.logoutSuccessUrl("/login.html") // 登出后跳转的url，默认是/login?logout
				// .logoutSuccessHandler(logoutSuccessHandler) //登出成功后进行处理的处理器
				.invalidateHttpSession(true)// 注销时让session失效
				// .addLogoutHandler(logoutHandler) //新增登出前调用处理器，可用于日志
				// .deleteCookies(cookieNamesToClear) //删除特定的cookie

				.and()// 附加新策略
				.csrf().disable(); // 关闭csrf，默认情况是开着的，会往页面写入隐藏的input输入token以防止csrf

		
		httpSecurity.addFilterAt(this.customUsernamePasswordAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 基于内存的用户存储和认证，认证成功后，给予USER角色(5.x以后版本需要passwordEncoder编码)
		// auth.inMemoryAuthentication().passwordEncoder(new
		// BCryptPasswordEncoder()).withUser("admin")
		// .password(new BCryptPasswordEncoder().encode("123456")).roles("USER");

		// 添加自定义身份验证服务
		 //auth.userDetailsService(customUserDetailsService).passwordEncoder(new
		 //BCryptPasswordEncoder());

		// 自定义认证者
		auth.authenticationProvider(customAuthenticationProvider());
	}
}
