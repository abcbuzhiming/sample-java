package com.youming.demospringsecurity.security.core.userdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 参考:9.2.2 The UserDetailsService
 * https://docs.spring.io/spring-security/site/docs/4.2.4.RELEASE/reference/htmlsingle/#tech-userdetailsservice
 * UserDetailsService纯粹是用于用户数据的DAO，除了将该数据提供给框架内的其他组件外(比如SecurityContextHolder)，不执行其他功能。
 * 特别是，它不认证用户，由AuthenticationManager完成。在许多情况下，如果您需要自定义身份验证过程，则直接实施AuthenticationProvider更有意义
 * 需要注意的是，最好不要在这个服务里访问数据库，因为任何从SecurityContextHolder获取用户认证数据的行为都会使用这个服务
 */
@Service
public class LoginSecurityUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority giAuthority = new SimpleGrantedAuthority("user");		//GrantedAuthority的默认实现都是基于role的
		grantedAuthorities.add(giAuthority);
		User user = new User(username, "", grantedAuthorities); // User是UserDetails的一个实现类
		return user;
	}

}
