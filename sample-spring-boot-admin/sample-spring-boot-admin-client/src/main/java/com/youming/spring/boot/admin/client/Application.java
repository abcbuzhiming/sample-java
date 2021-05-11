youming com.youming.spring.boot.admin.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youming.spring.boot.admin.client.config.SpringApplicationConfig;

/**
 * spring boot admin的客户端
 * */
public class Application {

	public static void main(String[] args) {
		System.out.println("hello youming-sample-spring-boot-admin-client");
		SpringApplication.run(SpringApplicationConfig.class, args);
	}
}
