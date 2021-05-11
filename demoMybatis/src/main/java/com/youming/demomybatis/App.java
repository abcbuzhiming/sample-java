package com.youming.demomybatis;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.youming.demomybatis.service.Test1Service;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello Mybatis test!" );
        
        @SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml"); // spring载入context
        //DataSource dataSource = (DataSource) applicationContext.getBean("dataSource2");
        Test1Service test1Service = applicationContext.getBean(Test1Service.class);
        test1Service.insert();
    }
}
