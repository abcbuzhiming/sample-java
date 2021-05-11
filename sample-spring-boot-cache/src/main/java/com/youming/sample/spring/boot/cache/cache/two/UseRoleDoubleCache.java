package com.youming.sample.spring.boot.cache.cache.two;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.youming.sample.spring.boot.cache.po.UserRole;
import com.youming.sample.spring.boot.cache.redis.UserRoleRedis;

/**
 * 用户角色缓存 双缓存结构，1级Caffenine，2级Redis
 * 注意，不要在该类中的方法内部调用被@Cacheable注解的方法，内部调用时调用的是自身，而不是代理过的对象，会导致cache不起效
 * Spring @Cacheable注解类内部调用不生效 https://blog.csdn.net/chengbinbbs/article/details/81128311
 */
@Component
@CacheConfig(cacheManager="caffeineCacheManager", cacheNames = "UserRole")
public class UseRoleDoubleCache {

	private static final Logger logger = LoggerFactory.getLogger(UseRoleDoubleCache.class);

	@Autowired
	private UserRoleRedis userRoleRedis;
	
	@Cacheable(key = "T(String).valueOf(#userType).concat('-').concat(#userNumber)", unless = "#result == null or #result.size() == 0")
	public List<UserRole> getUserRoleL1(Integer userType, String userNumber) {

		logger.info("getUserRoleL1查找源数据");
		List<UserRole> userRoleList = userRoleRedis.getUserRoleList(userType, userNumber);
		return userRoleList;
	}
	
	
	
	/**
	 * 让缓存失效
	 * */
	@CacheEvict(key = "T(String).valueOf(#userType).concat('-').concat(#userNumber)")
	public void delUserRoleCache(Integer userType, String userNumber) {
		userRoleRedis.delUserRoleCache(userType, userNumber);
	}
}
