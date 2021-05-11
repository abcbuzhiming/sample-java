package com.youming.bootswagger.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 全局Auth token演示
 * 全局参数存在一个问题，每个接口都需要输入一次参数。现在这个方法
 * 参考 ：https://www.jianshu.com/p/07a6d2ac9fed (注意这个链接的代码有关键错误，key对不上不会起效，具体看官方文档)
 * http://springfox.github.io/springfox/docs/current/#springfox-swagger2-with-spring-mvc-and-spring-boot
 * */
@Configuration
@EnableSwagger2
public class Swagger2ConfigAuthorize {
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
                .securitySchemes(securitySchemes())		//定义了一种协议头
                .securityContexts(securityContexts());		//定义具体起作用的内容
    }
    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("Sample API系统")
                //创建人
                .contact(new Contact("youming", "http://www.baidu.com", ""))
                //版本号
                .version("v1")		//获取项目在配置文件中定义的版本号
                //描述
                .description("spring boot swagger 范例说明")
                .build();
    }
    
    
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList= new ArrayList<ApiKey>();
        apiKeyList.add(new ApiKey("mykey", "x-auth-token", "header"));		//第一个参数是键，必须和下面的SecurityReference里的key对应,第二个参数是参数名称，第三个是以什么方式传上来，header是http头
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<SecurityContext>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        //.forPaths(PathSelectors.regex("^(?!auth).*$"))
                        //.forPaths(PathSelectors.regex("/aaa/*"))		//针对哪些路径起作用,可以过滤掉一些路径
                        .build());
        return securityContexts;
    }
    
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences=new ArrayList<>();
        securityReferences.add(new SecurityReference("mykey", authorizationScopes));	//第一个参数必须和上面ApiKey第一个参数对上，否则不起效
        return securityReferences;
    }

}
