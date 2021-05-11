package com.youming.bootswagger.config;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2ConfigGlobalParameter {
	@Autowired
	private Environment environment;
	
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.youming.bootswagger.controller"))		//控制器包路径
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(		//全局配置，可设定path，param，header
                    Arrays.asList(new ParameterBuilder()
                        .name("token")			//全局参数名称
                        .description("Description of header")		//全局参数描述
                        .modelRef(new ModelRef("string"))	//值类型，integer,string		
                        .parameterType("header")		//path(url),query(url查询字符串),form(表单),header(http头)
                        .defaultValue("1") 		//默认值
                        .required(false)
						.build()))
                ;
    }
    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("Sample Api API系统")
                //创建人
                .contact(new Contact("youming", "http://www.baidu.com", ""))
                //版本号
                .version("v1")		//获取项目在配置文件中定义的版本号
                //描述
                .description("spring boot swagger 范例说明")
                .build();
    }
}
