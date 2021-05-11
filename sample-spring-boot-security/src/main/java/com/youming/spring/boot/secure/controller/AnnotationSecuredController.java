package com.youming.spring.boot.secure.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

/**
 * Spring Security使用Secured 注解演示
 * */
@Controller
public class AnnotationSecuredController {

	//@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
	//public Account readAccount(Long id);

	//@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
	//public Account[] findAccounts();

	//@Secured("ROLE_TELLER")
	//public Account post(Account account, double amount);
	
}
