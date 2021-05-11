package com.youming.sample.spring.boot.redis.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youming.sample.spring.boot.redis.utils.SpringUtils;

@Controller
@RequestMapping("/sample")
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private RedisTemplate<String, String> template;

	// @Autowired
	// private ListOperations<String, String> listOps;

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "hello youming-sample-spring-boot-redis";
	}

	/**
	 * 测试最普通的KeyValue Redis的底层数据可以认为都是字符串 Redis 所有的数据结构都是以唯一的 key 字符串作为名称，然后通过这个唯一
	 * key 值来获取相应的 value 数据。不同类型的数据结构的差异就在于 value 的结构不一样
	 */
	@ResponseBody
	@RequestMapping("/testKeyValue")
	public String testKeyValue() {
		ValueOperations<String, String> ops = this.template.opsForValue();
		String key = "spring.boot.redis.test";
		boolean hasKey = this.template.hasKey(key); // 测试key是否存在
		ops.set(key, "foo"); // 设置key的值，不过期
		ops.set(key, "foo", 10, TimeUnit.SECONDS); // 设置key的值，10秒过期
		System.out.println("Found key " + key + ", value=" + ops.get(key));
		return "OK";
	}

	/**
	 * redis的list操作，实际上redis的list是个链表 但是它两边都可以入，也可以出，所以它可以同时描述队列，栈，链表三种数据结构
	 */
	@ResponseBody
	@RequestMapping("/testList")
	public String testList() {
		ListOperations<String, String> listOperations = this.template.opsForList();
		String key = "spring.boot.redis.test";
		listOperations.leftPush(key, "left 1"); // 左边入
		listOperations.leftPush(key, "left 2"); // 左边入
		listOperations.leftPush(key, "left 3"); // 左边入
		listOperations.rightPush(key, "right 1"); // 右边入
		listOperations.rightPush(key, "right 2"); // 右边入
		listOperations.rightPush(key, "right 3"); // 右边入
		
		List<String> list = listOperations.range(key, 0, listOperations.size(key)); // 获取所有的list
		System.out.println(list);
		listOperations.leftPop(key); // 左出
		listOperations.rightPop(key); // 右出
		list = listOperations.range(key, 0, listOperations.size(key)); // 获取所有的list
		System.out.println(list);
		// this.template.expire(key, 20, TimeUnit.SECONDS); //设定超时

		return "OK";
	}

	/**
	 * Redis 的字典相当于 Java 语言里面的 HashMap，它是无序字典。内部实现结构上同 Java 的 HashMap 也是一致的， 同样的数组 +
	 * 链表二维结构。第一维 hash 的数组位置碰撞时，就会将碰撞的元素使用链表串接起来
	 */
	@ResponseBody
	@RequestMapping("/testHash")
	public String testHash() {
		HashOperations<String,String,String> hashOperations = this.template.opsForHash();
		String key = "spring.boot.redis.test";
		hashOperations.put(key, "key1", "value1");		//插入数据
		hashOperations.put(key, "key2", "value2");
		hashOperations.put(key, "key3", "value3");
		hashOperations.hasKey(key, "key4");		//判断key是否存在
		Set<String> keySet = hashOperations.keys(key);		//得到所有key
		System.out.println(keySet);		
		return "OK";
	}
	
	/**
	 * set (集合)
	 * Redis 的集合相当于 Java 语言里面的 HashSet，它内部的键值对是无序的唯一的。它的内部实现相当于一个特殊的字典，字典中所有的 value 都是一个值NULL
	 * */
	@ResponseBody
	@RequestMapping("/testSet")
	public String testSet() {
		SetOperations<String,String>  setOperations = this.template.opsForSet();
		String key = "spring.boot.redis.test";
		setOperations.add(key, "java","python","python","golang");		//重复的不会被插入
		Set<String> set = setOperations.members(key);
		System.out.println(set);		//打印的顺序和插入并不一致，因为Set是无序的
		setOperations.pop(key);		//弹出一个,注意它只弹出处于最右侧的
		set = setOperations.members(key);
		System.out.println(set);
		return "OK";
		
	}
	
	/**
	 * zset (有序集合)
	 * 它类似于 Java 的 SortedSet 和 HashMap 的结合体，一方面它是一个 set，保证了内部 value 的唯一性，另一方面它可以给每个 value 赋予一个 score，
	 * 代表这个 value 的排序权重。它的内部实现用的是一种叫做「跳跃列表」的数据结构
	 * */
	@ResponseBody
	@RequestMapping("/testZSet")
	public String testZSet() {
		ZSetOperations<String,String>  zSetOperations= this.template.opsForZSet();
		String key = "spring.boot.redis.test";
		zSetOperations.add(key, "java", 0.4d);
		zSetOperations.add(key, "python", 0.2d);
		zSetOperations.add(key, "golang", 0.3d);
		Set<String> set = zSetOperations.rangeByScore(key, 0.0d, 1.0d);		//按照权重的范围返回
		System.out.println(set);
		return "OK";
	}
	
	/**
	 * 地理位置测试，redis3.2新功能
	 * */
	@ResponseBody
	@RequestMapping("/testGeo")
	public String testGeo(){
		GeoOperations<String, String> geoOperations= this.template.opsForGeo();
		String key = "spring.boot.redis.test";
		GeoLocation<String> location = new GeoLocation<String>("坐标1",new Point(0.01d, 0.02d));		//新建坐标1
		geoOperations.add(key, location);		//新增坐标
		geoOperations.add(key, new Point(0.03d, 0.04d), "坐标2");		//新建坐标2
		Distance distance = geoOperations.distance(key, "坐标1", "坐标2");		//计算距离
		System.out.println(distance);
		return "ok";
	}
}
