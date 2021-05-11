package com.youming.sample.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 线程工厂
 * */
public class LongEventFactory implements EventFactory<LongEvent> {

	@Override
	public LongEvent newInstance() {
		// TODO Auto-generated method stub
		return new LongEvent();
	}

}
