package com.youming.demobootoracle.utils;

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
    public ApplicationContext getApplicationContext() {  
        return SpringUtils.applicationContext;  
    }  
}
