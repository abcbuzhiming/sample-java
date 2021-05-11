package com.youming.alipay;

import com.youming.alipay.utils.alipayUtil.ALiPayUtil;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ALiPayUtil aLiPayUtil = new ALiPayUtil();
		aLiPayUtil.pay("我是测试数据", "App支付测试Java", "0.01", "70501111111S001111119");
	}

}
