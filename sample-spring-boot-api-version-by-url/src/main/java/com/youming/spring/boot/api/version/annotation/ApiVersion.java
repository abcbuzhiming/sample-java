youming com.youming.spring.boot.api.version.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

/**
 * 实现一个注解，用于传输api版本变量
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {

	/**
	 * 版本号 Function: url版本注解<br/>
	 * Reason: value书写格式 n_n，匹配规则为小于等于n_n最近的一个版本，示例如下： 
	 * 现有接口 v1_0,v1_1,v2_0,v2_3 
	 * 访问 v1_1/xxx 执行： v1_1 
	 * 访问 v1_2/xxx 执行： v1_1 
	 * 访问 v2_1/xxx 执行： v2_0 
	 * 访问 v4_0/xxx 执行： v2_3
	 * 
	 * @return
	 */
	String value();
}
