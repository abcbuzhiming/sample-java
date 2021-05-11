package com.youming.sample.spring.boot.cache.redis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.youming.sample.spring.boot.cache.po.UserRole;

@Component
@CacheConfig(cacheManager="redisCacheManager" ,cacheNames = "UserRole")
public class UserRoleRedis {

	private static final Logger logger = LoggerFactory.getLogger(UserRoleRedis.class);

	@Cacheable(key = "T(String).valueOf(#userType).concat('-').concat(#userNumber)", unless = "#result == null")
	public UserRole getUserRole(Integer userType, String userNumber) {

		logger.info("getUserRole查找源数据");
		UserRole userRole = new UserRole();
		userRole.setUserNumber(userNumber);
		return userRole;
	}
	
	@Cacheable(key = "T(String).valueOf(#userType).concat('-').concat(#userNumber)", unless = "#result == null or #result.size() == 0")
	public List<UserRole> getUserRoleList(Integer userType, String userNumber) {

		logger.info("getUserRoleList查找源数据");
		List<UserRole> userRoleList = new ArrayList<UserRole>();
		UserRole userRole = new UserRole();
		userRole.setUserNumber(userNumber);
		userRoleList.add(userRole);
		return userRoleList;
	}

	/**
	 * 让缓存失效
	 */
	@CacheEvict(key = "T(String).valueOf(#userType).concat('-').concat(#userNumber)")
	public void delUserRoleCache(Integer userType, String userNumber) {

	}
}
