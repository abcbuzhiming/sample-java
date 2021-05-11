package com.youming.sample.spring.data.jpa;

import org.springframework.boot.SpringApplication;

import com.youming.sample.spring.data.jpa.config.ApplicationConfig;
import com.youming.sample.spring.data.jpa.repository.CustomerRepository;
import com.youming.sample.spring.data.jpa.utils.SpringUtils;

/**
 * 旧参考文章，10年前，说的不符合现实：鉴于反复出现讨论hibernate适用性问题的帖子，这次希望有个定论 http://www.iteye.com/topic/148055
 * 如何对 JPA 或者 MyBatis 进行技术选型 http://www.spring4all.com/article/391
 * 目前发现的明显弱点：
 * Entity不够灵活，必须映射表
 * DDD的思维是并不关注数据库的实现，以领域模型为中心建模，这很难满足围绕数据库建模的需求——比如关联查询时JPA就要求数据库必须有对应的关联表结构
 * */
public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(ApplicationConfig.class, args);
		CustomerRepository customerRepository = SpringUtils.getBean(CustomerRepository.class);
		customerRepository.findByLastName("aa");
	}

}
