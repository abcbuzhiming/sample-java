package com.youming.demobeetlsql;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
import org.junit.experimental.theories.Theories;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.youming.demobeetlsql.mapper.Test1Mapper;
import com.youming.demobeetlsql.mapper.VerificationCodeMapper;
import com.youming.demobeetlsql.model.po.Test1;
import com.youming.demobeetlsql.model.po.VerificationCode;
import com.youming.demobeetlsql.service.TeacherInfoService;
import com.youming.demobeetlsql.service.Test1Service;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws SQLException {
		System.out.println("Hello BeetlSQL test!");
		
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml"); // spring载入context

		DataSource dataSource = (DataSource) applicationContext.getBean("dataSource2");
		/*DataSource dataSource = null;
		Map<String, String> config = new HashMap<String, String>();
		config.put("driverClassName" , "com.mysql.cj.jdbc.Driver");
		config.put("url" , "jdbc:mysql://127.0.0.1:3306/teacher_training_system_v1?serverTimezone=GMT%2B8");
		config.put("username" , "sa");
		config.put("password" , "bigplayer");
		try {
			dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(config);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		//System.out.println(dataSource.toString());
		
		ConnectionSource source = ConnectionSourceHelper.getSingle(dataSource);
		//ConnectionSource source = ConnectionSourceHelper.getSimple("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/teacher_training_system_v1?serverTimezone=GMT%2B8", "sa", "bigplayer@1418");
		
		DBStyle mysqlDBStyle = new MySqlStyle();
		// sql语句放在classpagth的/sql 目录下
		SQLLoader loader = new ClasspathLoader("/sql");
		UnderlinedNameConversion nc = new  UnderlinedNameConversion();
		// 最后，创建一个SQLManager,DebugInterceptor 不是必须的，但可以通过它查看sql执行情况
		SQLManager sqlManager = new SQLManager(mysqlDBStyle,loader,source,nc,new Interceptor[]{new DebugInterceptor()});
		
		/*
		Test1 test1 = new Test1();
		test1.setValueInt(19);
		test1.setValueVarchar("xiandafu");
		sqlManager.insert(test1);
		*/
		//Test1Service test1Service = applicationContext.getBean(Test1Service.class);
		//test1Service.getcount();
		/*Test1Mapper test1Mapper =  sqlManager.getMapper(Test1Mapper.class);  
		long count = test1Mapper.getCount();
		*/
		/*Test1Service test1Service = applicationContext.getBean(Test1Service.class);
		int count = test1Service.getcount();
		System.out.println(count);*/
		TeacherInfoService teacherInfoService = applicationContext.getBean(TeacherInfoService.class);
		teacherInfoService.getTeacherInfoByUserName("aaa");
		
		VerificationCode verificationCode = new VerificationCode();
		verificationCode.setId((long)1);
		VerificationCodeMapper verificationCodeMapper = sqlManager.getMapper(VerificationCodeMapper.class);
		verificationCode = verificationCodeMapper.templateOne(verificationCode);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(verificationCode.getCreateTime());
		System.out.println(formatter.format(verificationCode.getCreateTime()));
	}

}
