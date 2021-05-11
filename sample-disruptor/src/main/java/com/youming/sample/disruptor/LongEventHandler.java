package com.youming.sample.disruptor;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {

	/**
	 * arg1 正在处理的事件序号 
	 * arg2  是否是这批次的最后一个事件
	 * */
	@Override
	public void onEvent(LongEvent longEvent, long arg1, boolean arg2) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("event sequence:"+ arg1 + ";is final event:" + arg2 + ";event value:" + longEvent.getValue());
	}

}
