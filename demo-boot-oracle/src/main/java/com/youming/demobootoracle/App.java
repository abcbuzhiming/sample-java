package com.youming.demobootoracle;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.youming.demobootoracle.config.InContainerConfig;
import com.youming.demobootoracle.po.Bonus;
import com.youming.demobootoracle.service.BonusService;



public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello demo-boot-oracle!");
		ApplicationContext applicationContext = SpringApplication.run(InContainerConfig.class, args);
		BonusService bonusService = applicationContext.getBean(BonusService.class);
		List<Bonus> bonus =  bonusService.selectList(null);
		System.out.println(bonus);
	}

}
