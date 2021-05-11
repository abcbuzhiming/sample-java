youming com.youming.spring.boot.admin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.spring.boot.admin.server.config.SpringApplicationConfig;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * 文档：http://codecentric.github.io/spring-boot-admin/2.0.4/
 * 注意，admin的版本需要和spring boot版本对应，比如spring boot是2.0.x，admin也得用2.0.x，升级到2.1.x就会出错
 * https://github.com/codecentric/spring-boot-admin/issues/768
 * */


public class Application {

	public static void main(String[] args) {
		System.out.println("hello youming-sample-spring-boot-admin-server");
		SpringApplication.run(SpringApplicationConfig.class, args);
	}
}
