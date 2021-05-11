package com.youming.demobeetlsql.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.youming.demobeetlsql.mapper.Test1Mapper;
import com.youming.demobeetlsql.model.po.Test1;


@Service
public class Test1Service {
	private Test1Mapper test1Mapper;

	@Resource
	public void setTest1Mapper(Test1Mapper test1Mapper) {
		this.test1Mapper = test1Mapper;
	}
	
	public int getcount(){
		int count = this.test1Mapper.getCount();
		return count;
	}
	
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
	public void insert() {
		Test1 test1 = new Test1();
		test1.setValueInt(20);
		test1.setValueVarchar("testMybatis");
		this.test1Mapper.insert(test1);
		
		test1.setId(1);
		test1.setValueInt(21);
		test1.setValueVarchar("testMybatis2");
		this.test1Mapper.insert(test1);
	}
	
}
