package com.youming.project.name.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class GeneratorMySQL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dbUsername = "sa"; // 数据库账号
		String dbPassword = "youmingTest@2018&26g"; // 数据库账号
		//注意，Mybatis中tinyint(1)数据自动转化为boolean，所以不要用tinyint(1)字段，可以用tinyint(2)
		String dbUrl = "jdbc:mysql://221.234.36.70:3306/test?serverTimezone=GMT%2B8&useSSL=false"; // 数据库地址
		// 数据库驱动名称( mysql-connector-java 5版本)
		String dbDriverClassName = "com.mysql.jdbc.Driver";
		// 数据库驱动名称(mysql-connector-java 6版本，用于MySQL 8.0以上，dbUrl需要加时区)
		// String dbDriver = "com.mysql.cj.jdbc.Driver";

		final String projectPath = System.getProperty("user.dir"); // 获取当前开发路径
		// File file = new File(GeneratorMybatisPlus.class.getResource("/").getPath());
		// String projectPath = file.getPath().replace("\\target\\classes","");
		// //获取项目包根目录
		// System.out.println(f.getPath().replace("\\target\\classes",""));
		System.out.println("projectPath:" + projectPath);

		// 代码生成器
		AutoGenerator autoGenerator = new AutoGenerator();

		// 全局配置
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setOutputDir(projectPath + "/src/main/java"); // 输出的路径
		globalConfig.setAuthor("author"); // 作者
		globalConfig.setServiceName("%sService"); // 自定义service接口的生成名称
		globalConfig.setOpen(false); // 生成结束后打开路径
		globalConfig.setIdType(IdType.AUTO);		//ID生成策略，不定义的话，Oracle生成的实体类ID上没有@tableid
		globalConfig.setDateType(DateType.TIME_PACK);		//时间类型是否使用java8的新time类型
		//globalConfig.setEnableCache(false); // 关二级缓存
		//globalConfig.setBaseResultMap(true); // mapper xml文件生成resultMap
		//globalConfig.setBaseColumnList(true); // mapper xml文件生成Columnlist
		globalConfig.setOpen(false); // 生成结束后是否打开路径
		globalConfig.setFileOverride(true); // 覆盖已有文件
		autoGenerator.setGlobalConfig(globalConfig);

		// 数据源配置
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.MYSQL);
		dataSourceConfig.setUrl(dbUrl);
		dataSourceConfig.setUsername(dbUsername);
		dataSourceConfig.setPassword(dbPassword);
		dataSourceConfig.setDriverName(dbDriverClassName);
		autoGenerator.setDataSource(dataSourceConfig);

		// 包配置
		PackageConfig packageConfig = new PackageConfig();
		packageConfig.setParent("com.youming.project.name.data"); // 在这里，请把project.name改成你自己的项目包名字符串比如:whut.teach.system
		// packageConfig.setEntity("po"); //设置实体类的包名称
		autoGenerator.setPackageInfo(packageConfig);

		// 注入自定义配置
		InjectionConfig injectionConfig = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<String, Object>();
				this.setMap(map);
			}
		};
		// 调整 mybatis Mapper.xml 生成目录
		List<FileOutConfig> fileOutConfigList = new ArrayList<FileOutConfig>();
		fileOutConfigList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// System.out.println("xml目录：" + dir + "/src/main/resource/mybatis/" +
				// tableInfo.getEntityName() + "Mapper.xml");
				return projectPath + "/src/main/resources/mybatis/" + tableInfo.getEntityName() + "Mapper.xml"; // 调整后的路径
			}
		});
		//injectionConfig.setFileOutConfigList(fileOutConfigList);		//不生成mapper.xml的时候注释掉该行
		autoGenerator.setCfg(injectionConfig);

		// 配置模板
		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.setController(null); // 关闭controller层生成
		// templateConfig.setService(null); //关闭service层生成
		// templateConfig.setServiceImpl(null); //关闭ServiceImpl层生成
		// templateConfig.setMapper(null); //关闭Mapper层生成
		templateConfig.setXml(null); // 关闭默认Mybatis的Mapper.xml生成
		autoGenerator.setTemplate(templateConfig);
		
		// 策略配置
		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig.setNaming(NamingStrategy.underline_to_camel); // 下划线转驼峰命名
		strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel); // 数据库表字段映射到实体的命名策略
		//strategyConfig.setSuperEntityClass("com.youming.ant.common.BaseEntity");		//定义自己的父实体类(如果有需要的话)
		strategyConfig.setCapitalMode(true); // 是否大写
		//strategyConfig.setEntityLombokModel(true); // 开启为lombok模型(该模型需要IDE装插件才能支持)

		// 设置需要包含的表名,注意和setExclude只能2选1
		// String[] includeTableName = {"student_vip_purchase"};
		// strategyConfig.setInclude(includeTableName);

		// 设置需要排除的表名,注意和setInclude只能2选1
		// String[] excludeTableName = {"view_evaluation_all_teacher_statistical"};
		// strategyConfig.setExclude(excludeTableName);

		// strategyConfig.setTablePrefix( "table_"); //表前缀
		// strategyConfig.setFieldPrefix("filed_"); //字段前缀
		autoGenerator.setStrategy(strategyConfig);
		autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());

		autoGenerator.execute();		//开始生成
	}

}
