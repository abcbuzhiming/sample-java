package com.youming.demobootoracle.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.youming.demobootoracle.utils.SpringUtils;

@Configuration
@EnableAutoConfiguration // spring boot将根据你添加的依赖自动进行配置
@ComponentScan(basePackages = { "com.youming.demobootoracle" }) // 自动扫描包范围,相当于xml配置:context:component-scan
@EnableAspectJAutoProxy(proxyTargetClass = true) // 指定使用CGLIB代理，aop，相当于xml配置:<aop:aspectj-autoproxy
// proxy-target-class="true"/>
@EnableTransactionManagement // 事务
@PropertySource({ "classpath:application.properties", "classpath:dataSource.properties" }) // 注入多个配置文件如果是相同的key，则最后一个起作用
@Import({ MybatisConfig.class}) // 注入别的配置类
public class InContainerConfig extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder) {
		return springApplicationBuilder.sources(InContainerConfig.class);
	}

	@Autowired
	private Environment environment;

	// 生成数据源(bean id和method名称一致,除非另外指定)
	@Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
	public DruidDataSource dataSource() throws SQLException {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(environment.getProperty("jdbc.url"));
		druidDataSource.setUsername(environment.getProperty("jdbc.username"));
		druidDataSource.setPassword(environment.getProperty("jdbc.password"));
		druidDataSource.setInitialSize(1); // 初始化大小
		druidDataSource.setMinIdle(1); // 最小连接数
		druidDataSource.setMaxActive(20); // 最大连接数
		druidDataSource.setMaxWait(60000); // 配置获取连接等待超时的时间
		druidDataSource.setTimeBetweenEvictionRunsMillis(60000); // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		druidDataSource.setMinEvictableIdleTimeMillis(300000); // 配置一个连接在池中最小生存的时间，单位是毫秒
		//druidDataSource.setValidationQuery("SELECT 'x'");
		druidDataSource.setTestWhileIdle(true);
		druidDataSource.setTestOnBorrow(false);
		druidDataSource.setTestOnReturn(false);
		druidDataSource.setPoolPreparedStatements(true); // 打开PSCache，并且指定每个连接上PSCache的大小
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
		druidDataSource.setFilters("druidStatus"); // 配置监控统计拦截的filters，去掉后监控界面sql无法统计
		return druidDataSource;
	}

	/**
	 * Mybatis-Plus的sqlSessionFactory
	 */
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
		mybatisSqlSessionFactoryBean.setDataSource(this.dataSource());
		mybatisSqlSessionFactoryBean.setTypeAliasesPackage("com.youming.demobootoracle.po"); // 给领域对象注册别名，注册后可以直接使用类名，而不用使用全限定的类名（就是不用包含包名）
		MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
		mybatisConfiguration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
		mybatisConfiguration.setMapUnderscoreToCamelCase(true); // 驼峰转换
		mybatisConfiguration.setJdbcTypeForNull(JdbcType.NULL); // 部分数据库不识别默认的NULL类型;比如oracle，需要配置该属性
		mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
		mybatisSqlSessionFactoryBean.setPlugins(new Interceptor[] { new PaginationInterceptor(), // 分页插件配置
				// new PerformanceInterceptor(), // 性能拦截器，兼打印sql，不建议生产环境配置
				new OptimisticLockerInterceptor() // 乐观锁插件
		});
		// 添加XML目录
		List<Resource> resources = new ArrayList<Resource>();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		resources.addAll(Arrays.asList(resolver.getResources("classpath*:/mybatis/*Mapper.xml")));
		resources.addAll(Arrays.asList(resolver.getResources("classpath*:/mybatis/relate/*Mapper.xml")));
		mybatisSqlSessionFactoryBean.setMapperLocations(resources.toArray(new Resource[resources.size()])); // 扫描Mybatis的映射文件，强制要求以Mapper结尾
		// 定义 Mybatis-Plus 全局策略
		GlobalConfiguration globalConfiguration = new GlobalConfiguration();
		// 逻辑删除 定义下面3个参数(不需要这个)
		// globalConfiguration.setSqlInjector(new LogicSqlInjector()); // 逻辑删除Sql注入器
		// globalConfiguration.setLogicDeleteValue("-1");
		// globalConfiguration.setLogicNotDeleteValue("1");
		// 全局ID类型： 0, "数据库ID自增"， 1, "用户输入ID", 2, "全局唯一ID", 3, "全局唯一ID"
		globalConfiguration.setIdType(0);

		mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfiguration);
		return mybatisSqlSessionFactoryBean.getObject();
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager() throws SQLException {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(this.dataSource());
		return dataSourceTransactionManager;
	}

	// 专门用于注入保留ApplicationContext的工具类
	@Bean(name = "springUtils")
	public SpringUtils springUtils() {
		SpringUtils springUtils = new SpringUtils();
		return springUtils;
	}

}
