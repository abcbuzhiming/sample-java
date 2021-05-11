package com.youming.spring.boot.secure.security.authentication;

import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.youming.spring.boot.secure.service.UserService;
import com.youming.spring.boot.secure.utils.SpringUtils;

/**
 * 自定义认证处理器，仿照自DaoAuthenticationProvider
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	/**
	 * 认证过程
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		logger.info("执行CustomAuthenticationProvider.authenticate");
		CustomUsernamePasswordAuthenticationToken customUsernamePasswordAuthenticationToken = (CustomUsernamePasswordAuthenticationToken) authentication;
		Integer type = Integer.valueOf(customUsernamePasswordAuthenticationToken.getType().toString());
		String username = customUsernamePasswordAuthenticationToken.getPrincipal().toString();
		String password = customUsernamePasswordAuthenticationToken.getCredentials().toString();

		// 这里载入自定义Service，检查用户是否存在等信息
		UserService userService = SpringUtils.getBean(UserService.class);
		Map<String, String> userMap = userService.loadUser();
		if (userMap ==  null) { // 用户名不存在
			throw new UsernameNotFoundException("用户不存在！");
		}
		if (!userMap.get("password").equals("123456")) {	//密码错误
			logger.debug("Authentication failed: password does not match stored value");
			throw new BadCredentialsException("Bad credentials");
		}
		

		// 封装权限信息，并且此时身份已经被认证
		return new CustomUsernamePasswordAuthenticationToken(type, username, password,
				Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
	}

	/**
	 * 支持哪些token,可以增加其他的token
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return (CustomUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
