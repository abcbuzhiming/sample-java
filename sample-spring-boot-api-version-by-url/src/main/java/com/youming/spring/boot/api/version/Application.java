youming com.youming.spring.boot.api.version;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.spring.boot.api.version.config.SpringApplicationConfig;

/**
 * spring webmvc,api接口，基于路径的多版本控制
 * 参考:
 * https://luoluonuoya.github.io/2017/11/10/springmvc实现restful api版本控制并兼容swagger/
 * https://www.hifreud.com/2018/01/30/01-API-versioning/
 * 
 * 
 * */
public class Application {

	public static void main(String[] args) {
		System.out.println("hello youming-sample-spring-boot-api-version");
		SpringApplication.run(SpringApplicationConfig.class, args);
	}
}
