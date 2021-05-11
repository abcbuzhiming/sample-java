package com.youming.spring.boot.scheduling.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步任务演示
 * */
@Component
public class AsyncLogic {
	
	private static final Logger logger = LoggerFactory.getLogger(AsyncLogic.class);
	
	
	@Async
	public void test() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("test Thread.sleep(2000) end");
	}
}
