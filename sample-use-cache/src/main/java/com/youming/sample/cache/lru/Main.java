package com.youming.sample.cache.lru;

/**
 * 你应该知道的缓存进化史 https://mp.weixin.qq.com/s/wnPrE4MglmCFxyAwtTh_5A
 * 使用自定义的LRUMap来实践LRU的算法
 * */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LRUMap<Integer,String> map = new LRUMap<>(5, "lock");
		map.put(1, "cache 1");
		map.put(2, "cache 2");
		map.put(3, "cache 3");
		map.put(4, "cache 4");
		map.put(5, "cache 5");
		
		System.out.println(map);		//输出默认的顺序
		
		map.get(2);		//读2号缓存
		System.out.println(map);		//会发现2号缓存被排到队尾
		map.get(3);		//读3号缓存
		System.out.println(map);		//会发现3号缓存被排到队尾
		
		map.put(6, "cache 6");		//新加入缓存
		System.out.println(map);		//新加入缓存排在队尾，队头部的1号缓存因为整个缓存超过了最大容量导致被淘汰
	}

}
