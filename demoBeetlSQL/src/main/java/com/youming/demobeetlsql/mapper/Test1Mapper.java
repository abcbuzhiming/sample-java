package com.youming.demobeetlsql.mapper;


import java.util.List;
import java.util.Map;

import org.beetl.sql.core.mapper.BaseMapper;

import com.youming.demobeetlsql.model.po.Test1;


public interface Test1Mapper extends BaseMapper<Test1> {
    
	public int getCount();
	
	//public int insert(Test1 test1);

	//Test1 selectUserSingleGoods(Test1 test1);		//获取单一物品数量
    
    
}