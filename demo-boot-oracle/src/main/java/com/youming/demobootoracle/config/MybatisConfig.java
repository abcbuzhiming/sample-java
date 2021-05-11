package com.youming.demobootoracle.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;

/**
 * MapperScannerConfigurer它实现了BeanDefinitionRegistryPostProcessor。这个接口在是Spring扫描Bean定义时会回调的，
 * 远早于BeanFactoryPostProcessor。所以如果这个bean初始化和别的bean放一起，会导致它初始化时，别的bean都是空的，于是就出错了。
 * 解决办法:把MapperScannerConfigurer放入独立的的config，先使主config把其它对象和属性初始化，最后再上这个MapperScannerConfigurer
 * */
public class MybatisConfig {

	// DAO接口所在包名，Spring会自动查找其下的类
	@Bean(name = "mapperScannerConfigurer")
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");		//这里的名字要和SqlSessionFactory的名字一致
        mapperScannerConfigurer.setBasePackage("com.youming.demobootoracle.mapper");		//扫描所在包
        mapperScannerConfigurer.setAnnotationClass(Mapper.class);		//扫描Mapper注解的类
        return mapperScannerConfigurer;
    }
}
