package com.youming.sample.cache.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 你应该知道的缓存进化史 https://mp.weixin.qq.com/s/wnPrE4MglmCFxyAwtTh_5A
 * */
public class LRUMap<K, V> extends LinkedHashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3983119539686881292L;

	private final int max;

	private Object lock;

	public LRUMap(int max, Object lock) {
		//初始容量：max的1.4倍；0.75f加载因子(扩容时用初始容量*加载因子)；排序方式 false 基于插入顺序  true  基于访问顺序
		//这样，最近访问的数据在队尾，淘汰的都在队首
		super((int) (max * 1.4f), 0.75f, true);		
		this.max = max;
		this.lock = lock;
	}

	/**
	 * 在LinkedHashMap中维护了一个entry(用来放key和value的对象)链表。在每一次get或者put的时候都会把插入的新entry，或查询到的老entry放在我们链表末尾。 
	 * 我们在构造方法中，设置的大小特意设置到max*1.4，在下面的removeEldestEntry方法中只需要size>max就淘汰，这样我们这个map永远也走不到扩容的逻辑了
	 * */
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return this.size() > max;
	}
	
	
	public Object getValue(Object key) {
		synchronized (lock) {
			return get(key);
		}
	}
	
	public void putValue(K key,V value) {
		synchronized (lock) {
			this.put(key, value);
		}
		
	}
}
