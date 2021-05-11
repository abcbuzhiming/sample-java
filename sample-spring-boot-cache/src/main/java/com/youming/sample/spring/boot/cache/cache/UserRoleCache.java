package com.youming.sample.spring.boot.cache.cache;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.youming.sample.spring.boot.cache.po.UserRole;

/**
 * @CacheConfig和@Cacheable都有一个cacheNames属性，这个属性可以设置多个值，即可以针对多个缓存实现读取和存储，
 * 但是需要注意的是，这多个缓存必须属于同一个cacheManager。也就是说spring-boot的cache体系不支持垮缓存管理器。只能自己实现
 * 
 * Spring Cache注解不支持expire的问题 https://www.jianshu.com/p/9255b2484818  
 * spring cache的注解是不支持单独设置过期时间的，因为每种cache的实现有差别。所以最好的方式的每类cache定义一个统一的失效时间
 * */

/**
 * 用户角色缓存
 */
@Component
@CacheConfig(cacheNames = "UserRole")
public class UserRoleCache {

	private static final Logger logger = LoggerFactory.getLogger(UserRoleCache.class);

	/**
	 * 默认key的生成按照以下规则： 如果没有参数,则使用0作为key 如果只有一个参数，使用该参数作为key
	 * 如果又多个参数，使用包含所有参数的hashCode作为key
	 */
	
	/**
	 * 每次都是先执行方法，然后写缓存
	 * */
	//@CachePut(key = "T(String).valueOf(#userType).concat('-').concat(#userNumber)", unless = "#result == null or #result.size() == 0")
	/**
	 * 先从缓存中读取，如果缓存没有就执行方法
	 * */
	@Cacheable(key = "T(String).valueOf(#userType).concat('-').concat(#userNumber)", unless = "#result == null or #result.size() == 0")
	public List<UserRole> getUserRole(Integer userType, String userNumber) {

		logger.info("getUserRole查找源数据");
		List<UserRole> userRoleList = new ArrayList<UserRole>();
		UserRole userRole = new UserRole();
		userRole.setUserNumber(userNumber);
		userRoleList.add(userRole);
		return userRoleList;
	}

	/**
	 * 让缓存失效
	 * */
	@CacheEvict(key = "T(String).valueOf(#userType).concat('-').concat(#userNumber)")
	public void delUserRoleCache(Integer userType, String userNumber) {

	}
}
