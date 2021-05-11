package com.youming.spring.boot.secure.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 仿UsernamePasswordAuthenticationToken的自定义token
 * */
public class CustomUsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3267872317934504374L;
	
	private final Object type;		//用户类型
	private final Object principal;		//用户身份
	private Object credentials;		//
	
	
	/**
	 * 第一个构造器是用于认证之前，传递给认证器使用的
	 * This constructor can be safely used by any code that wishes to create a
	 * <code>UsernamePasswordAuthenticationToken</code>, as the {@link #isAuthenticated()}
	 * will return <code>false</code>.
	 *
	 */
	public CustomUsernamePasswordAuthenticationToken(Object type,Object principal, Object credentials) {
		super(null);
		this.type = type;
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(false);		//注意这个构造方法是认证时使用的
	}
	
	
	/**
	 * This constructor should only be used by <code>AuthenticationManager</code> or
	 * <code>AuthenticationProvider</code> implementations that are satisfied with
	 * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
	 * authentication token.
	 *
	 * @param type
	 * @param principal
	 * @param credentials
	 * @param authorities
	 */
	public CustomUsernamePasswordAuthenticationToken(Object type,Object principal, Object credentials,Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.type = type;
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);		//注意这个构造方法是认证成功后使用的
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return this.principal;
	}


	public Object getType() {
		return type;
	}
	
	

}
