package com.youming.demospringsecurity.security.authentication;

import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

/**
 * 自定义AuthenticationManager
 * 类似shiro里自定义ModularRealmAuthenticator(CustomizedAuthenticationStrategy)
 * */
public class CustomAuthenticationManager extends ProviderManager {

	public CustomAuthenticationManager(List<AuthenticationProvider> providers) {
		super(providers);
		// TODO Auto-generated constructor stub
	}

}
