package com.youming.demobootoracle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

//MyBatis-Plus的代码生成方案，本生成方案只更新PO和Maper.xml
public class GeneratorMybatisPlus {

	public static void main(String[] args) {
		Properties properties = new Properties();
		InputStream inputStream = GeneratorMybatisPlus.class.getResourceAsStream("/dataSource.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		//String dir = System.getProperty("user.dir");		//获取当前开发路径
		File f = new File(GeneratorMybatisPlus.class.getResource("/").getPath());
		String dir = f.getPath().replace("\\target\\classes","");
		//System.out.println(f.getPath().replace("\\target\\classes",""));
		//System.out.println(dir);
		GlobalConfig globalConfig = new GlobalConfig();
		String dbUrl = properties.getProperty("jdbc.url");
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.ORACLE).setUrl(dbUrl).setUsername(properties.getProperty("jdbc.username"))
				.setPassword(properties.getProperty("jdbc.password")).setDriverName(properties.getProperty("jdbc.driverClassName"));
		StrategyConfig strategyConfig = new StrategyConfig();
		//String[] includeTableName = {"student_vip_purchase"};
		String[] excludeTableName = {"p_video"};		//注意mybatis-plus不能针对Blob的mysql类型正确映射，因此需要手动
		strategyConfig.setCapitalMode(true).setEntityLombokModel(false).setDbColumnUnderline(true)
				//.setInclude(strings) //修改替换成你需要的表名，多个表名传数组.不设置这项就生成全部
				.setExclude(excludeTableName)
				.setNaming(NamingStrategy.underline_to_camel);
		globalConfig.setActiveRecord(false).setAuthor("Author") // 作者名
				.setServiceName("%sService") // 自定义service接口的生成名称
				.setOpen(false) // 生成结束后打开路径
				.setEnableCache(false) // 关二级缓存
				.setBaseResultMap(true) // mapper xml文件生成resultMap
				.setBaseColumnList(true) // mapper xml文件生成Columnlist
				//.setOutputDir("F:/POJO生成") // 输出目录，根据自己的情况修改
				.setOutputDir(dir + "/src/main/java/") // 输出目录，根据自己的情况修改
				.setFileOverride(true);

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
		fileOutConfigList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
            	//System.out.println("xml目录：" + dir + "/src/main/resource/mybatis/" + tableInfo.getEntityName() + "Mapper.xml");
                return dir + "/src/main/resources/mybatis/" + tableInfo.getEntityName() + "Mapper.xml";		//调整后的路径
            }
        });
		injectionConfig.setFileOutConfigList(fileOutConfigList);
		// 关闭默认 xml 生成，使用调整后的生成模型
        TemplateConfig templateConfig = new TemplateConfig();
        
        templateConfig.setController(null);		//关闭controller层生成
        //templateConfig.setService(null);		//关闭service层生成
        //templateConfig.setServiceImpl(null);	//关闭ServiceImpl层生成
        //templateConfig.setMapper(null);		//关闭Mapper层生成
        templateConfig.setXml(null);		//关闭默认Mybatis的Mapper.xml生成
        
		new AutoGenerator().setGlobalConfig(globalConfig).setDataSource(dataSourceConfig).setStrategy(strategyConfig)
				.setPackageInfo(new PackageConfig().setParent("com.youming.demobootoracle")
						.setController("controller").setEntity("po"))
				.setCfg(injectionConfig).setTemplate(templateConfig).execute();

	}

}
