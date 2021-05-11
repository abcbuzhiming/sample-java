package com.youming.samplespringdataelasticsearch.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 用来获取spring应用上下文的类
 * */
public class SpringUtils implements ApplicationContextAware {

	// Spring应用上下文环境  
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		if(SpringUtils.applicationContext == null) {
			SpringUtils.applicationContext = applicationContext;
        }
	}

	/** 
     * @return ApplicationContext 
     */  
    public static ApplicationContext getApplicationContext() {  
        return SpringUtils.applicationContext;  
    }
    
    public static <T> T getBean(Class<T> requiredType) {
		// TODO Auto-generated method stub
    	return SpringUtils.applicationContext.getBean(requiredType);
	}
}
