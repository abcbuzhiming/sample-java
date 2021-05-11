# sample-use-cache
Java的缓存进化历史和现状

## 参考
[你应该知道的缓存进化史](https://mp.weixin.qq.com/s/wnPrE4MglmCFxyAwtTh_5A)
[闲话缓存：ZFS 读缓存深入研究-ARC（一）](http://blog.chinaunix.net/uid-28466562-id-3837685.html)
[ehcache](https://github.com/ben-manes/caffeine/wiki/Ehcache)

## 历史
* 最原始的用HashMap
* 需要数据淘汰，进化出了LRUHashMap，人们就发明了几种淘汰算法，常见的三种FIFO,LRU,LFU（还有一些ARC,MRU）

## 淘汰算法
* FIFO，First Input First Output的缩写，先入先出队列，在这种淘汰算法中，先进入缓存的会先被淘汰。这种可谓是最简单的了，但是会导致我们命中率很低。
* LRU，Least Recently Used，最近最少使用。在这种算法中避免了上面的问题，每次访问数据都会将其放在我们的队尾，如果需要淘汰数据，就只需要淘汰队首即可。但是这个依然有个问题，如果有个数据在1个小时的前59分钟访问了1万次(可见这是个热点数据),再后一分钟没有访问这个数据，但是有其他的数据访问，就导致了我们这个热点数据被淘汰。
* LFU，Least Frequently Used，最近最少频率使用，在这种算法中又对上面进行了优化，利用额外的空间记录每个数据的使用频率，然后选出频率最低进行淘汰。这样就避免了LRU不能处理时间段的问题。
* ARC，Adaptive Replacement Cache，自适应缓存替换算法，在IBM Almaden研究中心开发,后由Solaris ZFS发展成为ARC(Adjustable Replacement Cache)，这个缓存算法同时跟踪记录LFU和LRU，以及驱逐缓存条目，来获得可用缓存的最佳使用
* MRU，Most Recently Used，最近最常使用算法，和LRU类似，差别在于它是按使用的频度来排序，按最少使用的数据最先被替换出去的规则进行替换


## LRU的问题
在近代社会中已经发明出来了LRUMap,用来进行缓存数据的淘汰，但是有几个问题:
* 锁竞争严重，可以看见我的代码中，Lock是全局锁，在方法级别上面的，当调用量较大时，性能必然会比较低。
* 不支持过期时间
* 不支持自动刷新

## caffeine vs ehcache
caffeine wiki下有篇文章认为ehcache的性能很糟糕