# JMH-samples
这是JMH(Java Microbenchmark Harness)性能测试范例
JMH的开发者告诫过JMH得到的仅仅是数据，不要过分依赖这些数据，要搞清楚为什么

## 参考
[Java并发计数器探秘](https://mp.weixin.qq.com/s/we3wCaArfPF5WzYYkB1zoA)
[JAVA拾遗 — JMH与8个代码陷阱](https://mp.weixin.qq.com/s/f0t-dcFt_HibvZY2_7Dh7Q)
[基准测试框架JMH（上）](https://time.geekbang.org/column/article/40275)
[基准测试框架JMH（下）](https://time.geekbang.org/column/article/40281)
[代码来源](https://github.com/lexburner/JMH-samples.git)

## JMH测量精度
毫秒级别的测试并不是很困难
微秒级别的测试是具备挑战性的，但并非无法完成，JMH 就做到了
纳秒级别的测试，目前还没有办法精准测试
皮秒级别…Holy Shit

## 测试结果读取
Benchmark                    Mode  Cnt  Score    Error  Units
FirstBenchMark.stringConcat  avgt    5  0.006 ±  0.001  us/op

Mode：测试模式
Cnt:次数
Score:耗时
Error:误差
Units：时间单位/每次操作

## JMH注解使用
* @State，基准测试的范围，标注在类上，在哪个作用域范围共享线程，
  1. Scope.Thread：默认的State，每个测试线程分配一个实例；
  2. Scope.Benchmark：所有测试线程共享一个实例，用于测试有状态实例在多线程共享下的性能；
  3. Scope.Group：每个线程组共享一个实例；
* @BenchmarkMode 测试的模式
* @OutputTimeUnit 提供报告结果的默认时间单位
* @Warmup 预热机制，某些代码在多次执行后会被JVM进行JIT优化而提速，因此使用预热机制触发JVM优化机制
* @Measurement 测量注释允许设置默认测量参数
* @Threads 线程数
* @Fork 循环次数
* @Setup 方法注解，会在执行 benchmark 之前被执行，正如其名，主要用于初始化
* @TearDown 方法注解，与@Setup 相对的，会在所有 benchmark 执行结束以后执行，主要用于资源的回收等。
* @Benchmark 方法注解，表示该方法是需要进行基准测试的对象
* @Param 成员注解，可以用来指定某项参数的多种情况。特别适合用来测试一个函数在不同的参数输入的情况下的性能。比较特别的是传入枚举，会遍历枚举的所有值

## 遇到的问题
* 执行报错：Exception in thread "main" java.lang.RuntimeException: ERROR: Unable to find the resource: /META-INF/BenchmarkList 
解决办法，run maven clean，然后重新生成一次就好
[JMH使用说明](https://blog.csdn.net/lxbjkben/article/details/79410740)