package com.youming.demomybatis.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;

import com.youming.demomybatis.model.po.Test1;



public interface Test1Mapper {
    
	@Insert("insert into test1 (`id`,`value_int`, `value_varchar`) values(#{id},#{valueInt}, #{valueVarchar})") 
	public int insert(Test1 test1);

	//Test1 selectUserSingleGoods(Test1 test1);		//获取单一物品数量
    
    
}