package com.youming.sample.mybatis.plus;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.youming.sample.mybatis.plus.entity.UserInfo;
import com.youming.sample.mybatis.plus.mapper.UserInfoMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 本范例演示mybatis-plus不使用spring时的使用
 * 其实并不完善，必须依赖xml配置
 * */
public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 数据源
		/*
		HikariConfig hikariConfig = new HikariConfig(); // 配置文件
		hikariConfig.setJdbcUrl(
				"jdbc:mysql://127.0.0.1:3306/test1?serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true");// oracle
		hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
		hikariConfig.setUsername("sa");
		hikariConfig.setPassword("youmingTest@2018&26g");
		// hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
		// hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
		// hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		 */
		
		// 使用MyBatis提供的Resources类加载mybatis的配置文件
		Reader reader;
		SqlSessionFactory sqlSessionFactory = null;
		try {
			reader = Resources.getResourceAsReader("mybatis.cfg.xml");
			sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 构建sqlSession的工厂
		if (sqlSessionFactory == null) {
			return;
		}
		SqlSession session = sqlSessionFactory.openSession(true);	//自动提交
		UserInfoMapper userInfoMapper = session.getMapper(UserInfoMapper.class);		//获取Mapper
		userInfoMapper.selectList(new QueryWrapper<UserInfo>().eq("name", "1"));
	}

}
