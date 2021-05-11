package com.youming.bootjsonbody.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.ValueConstants;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestJson {
	
	/**
     * 字段名，不填则默认参数名
     * @return
     */
	@AliasFor("name")
	String value() default "";
	
	/**
	 * value的别名
	 * */
	@AliasFor("value")
    String name() default "";

    /**
     * 默认值，不填则默认为null。
     * @return
     */
    String defaultValue() default ValueConstants.DEFAULT_NONE;
    
    /**
     * 是否必须，默认是必须的
     * */
    boolean required() default true;
}
