package com.youming.sample.spring.data.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youming.sample.spring.data.jpa.dto.Customer;

/**
 * spring data jpa对单表查询主要有三种
 * 1. 方法名直接映射为sql
 * 2. @query注解
 * 3. 使用Specification通过Criteria API进行查询(推荐)
 * */

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	List<Customer> findByLastName(String lastName);
}
