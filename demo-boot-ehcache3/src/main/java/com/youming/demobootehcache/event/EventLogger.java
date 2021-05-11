package com.youming.demobootehcache.event;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
*
* @author youming
* 缓存事件监听器
*/
public class EventLogger implements CacheEventListener<Object, Object> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventLogger.class);

	@Override
	public void onEvent(CacheEvent<? extends Object, ? extends Object> event) {
		// TODO Auto-generated method stub
		LOGGER.info("Event: " + event.getType() + " Key: " + event.getKey() + " old value: " + event.getOldValue()
				+ " new value: " + event.getNewValue());
	}

}
