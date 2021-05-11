# sample-disruptor
Disruptor框架的应用范例

## 参考
[还在用BlockingQueue？读这篇文章，了解下Disruptor吧](https://mp.weixin.qq.com/s/VLJ7fS4WjDFvj3ucWVe6bg)
[Disruptor并发框架](https://www.cnblogs.com/sigm/p/6251910.html)
[Disruptor系列2：Disruptor原理剖析](https://blog.csdn.net/twypx/article/details/80387761)
[Disruptor系列3：Disruptor样例实战](https://blog.csdn.net/twypx/article/details/80398886)

## Disruptor的原理
* CAS
* 消除伪共享
* RingBuffer 有了这三大杀器，Disruptor才变得如此牛逼。
以上3点使Disruptor的速度极快

可以将disruptor理解为，基于事件驱动的高效队列、轻量级的JMS