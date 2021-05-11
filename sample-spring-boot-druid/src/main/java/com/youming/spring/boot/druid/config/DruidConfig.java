package com.youming.spring.boot.druid.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfig {

	@Autowired
	private Environment environment;

	// 生成数据源(bean id和method名称一致,除非另外指定)
	@Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
	public DataSource dataSource() throws SQLException {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(environment.getProperty("spring.datasource.url"));
		druidDataSource.setUsername(environment.getProperty("spring.datasource.username"));
		druidDataSource.setPassword(environment.getProperty("spring.datasource.password"));
		druidDataSource.setInitialSize(10); // 初始化连接数大小
		druidDataSource.setMinIdle(8); // 最小连接数
		druidDataSource.setMaxActive(20); // 最大连接数
		druidDataSource.setMaxWait(60000); // 配置获取连接等待超时的时间
		druidDataSource.setTimeBetweenEvictionRunsMillis(60000); // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		druidDataSource.setMinEvictableIdleTimeMillis(300000); // 配置一个连接在池中最小生存的时间，单位是毫秒
		druidDataSource.setValidationQuery("SELECT 'x'");
		druidDataSource.setTestWhileIdle(true);
		druidDataSource.setTestOnBorrow(false);
		druidDataSource.setTestOnReturn(false);
		druidDataSource.setPoolPreparedStatements(true); // 打开PSCache，并且指定每个连接上PSCache的大小
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
		druidDataSource.setFilters("stat,wall,log4j"); // 配置监控统计拦截的filters，去掉后监控界面sql无法统计
		return druidDataSource;
	}

	/**
	 * https://github.com/alibaba/druid/wiki/配置_StatViewServlet配置
	 * Druid内置提供了一个StatViewServlet用于展示Druid的统计信息。
	 * 这个StatViewServlet的用途包括：提供监控信息展示的html页面,提供监控信息的JSON API
	 */
	@Bean
	public ServletRegistrationBean<StatViewServlet> ServletRegistrationBeanStatViewServlet() {
		ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<StatViewServlet>();
		servletRegistrationBean.setServlet(new StatViewServlet());
		servletRegistrationBean.addUrlMappings("/druid/*");		//url映射
		servletRegistrationBean.addInitParameter("loginUsername", "abc"); // 监控系统账号
		servletRegistrationBean.addInitParameter("loginPassword", "123"); // 监控系统密码
		//访问控制,deny优先于allow，如果在deny列表中，就算在allow列表中，也会被拒绝。
		//如果allow没有配置或者为空，则允许所有访问。
		//ip配置规则.<IP>或者<IP>/<SUB_NET_MASK_size>
		//例子：128.242.127.1/24。24表示，前面24位是子网掩码，比对的时候，前面24位相同就匹配。
		//由于匹配规则不支持IPV6，配置了allow或者deny之后，会导致IPV6无法访问。
		//servletRegistrationBean.addInitParameter("allow", "128.242.127.1/24,128.242.128.1");
		//servletRegistrationBean.addInitParameter("deny", "128.242.127.4");
		//在StatViewSerlvet输出的html页面中，有一个功能是Reset All，执行这个操作之后，会导致所有计数器清零，重新计数。你可以通过配置参数关闭它
		//servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	/**
	 * https://github.com/alibaba/druid/wiki/配置_配置WebStatFilter
	 * WebStatFilter用于采集web-jdbc关联监控的数据
	 */
	@Bean
	public FilterRegistrationBean<WebStatFilter> filterRegistrationBeanWebStatFilter() {
		FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>();
		filterRegistrationBean.setFilter(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");		//起效的url地址
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");		//排除的url
		filterRegistrationBean.addInitParameter("profileEnable", "true");		//配置profileEnable能够监控单个url调用的sql列表
		
		//缺省sessionStatMaxCount是1000个。你可以按需要进行配置
		//filterRegistrationBean.addInitParameter("sessionStatMaxCount", "1000");
		//你可以关闭session统计功能
		//filterRegistrationBean.addInitParameter("sessionStatEnable", "false");
		//你可以配置principalSessionName，使得druid能够知道当前的session的用户是谁
		//filterRegistrationBean.addInitParameter("principalSessionName", "xxx.user");
		//如果你的user信息保存在cookie中，你可以配置principalCookieName，使得druid知道当前的user是谁
		//filterRegistrationBean.addInitParameter("principalCookieName", "xxx.user");
		//filterRegistrationBean.setOrder(1);		//这是过滤器的顺序,order的数值越小，优先级越高
		return filterRegistrationBean;
	}
}
