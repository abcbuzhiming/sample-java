package com.youming.demobeetlsql;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.ConnectionSource;
import org.beetl.sql.core.ConnectionSourceHelper;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.SQLLoader;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.MapperCodeGen;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UtilsBeetlsqlGenerator {
	
	
	public static void main(String[] args) throws SQLException{
		System.out.println("Hello BeetlSQL Generator!");

		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml"); // spring载入context
		
		DataSource dataSource = (DataSource) applicationContext.getBean("dataSource2");
		ConnectionSource source = ConnectionSourceHelper.getSingle(dataSource);
		//ConnectionSource source = ConnectionSourceHelper.getSimple("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/teacher_training_system_v1?serverTimezone=GMT%2B8", "sa", "bigplayer@1418");
		
		DBStyle mysqlDBStyle = new MySqlStyle();
		// sql语句放在classpagth的/sql 目录下
		SQLLoader loader = new ClasspathLoader("/sql");
		UnderlinedNameConversion nc = new  UnderlinedNameConversion();
		// 最后，创建一个SQLManager,DebugInterceptor 不是必须的，但可以通过它查看sql执行情况
		SQLManager sqlManager = new SQLManager(mysqlDBStyle,loader,source,nc,new Interceptor[]{new DebugInterceptor()});
		
		try {
			
			sqlManager.genPojoCodeToConsole("transaction_btc_info");		//pojo类
			sqlManager.genSQLTemplateToConsole("transaction_btc_info");		//sql片段
			sqlManager.genSQLFile("transaction_btc_info");					//sql文件
			
			
			
			/*
			MapperCodeGen mapper = new MapperCodeGen("com.dao");
			GenConfig config = new GenConfig();
			config.preferBigDecimal(true);
			config.setBaseClass("com.youming.demobeetlsql.model.po.Test1");
			config.codeGens.add(mapper);
			sqlManager.genPojoCodeToConsole("test1", config);
			*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
