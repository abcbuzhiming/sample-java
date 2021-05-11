# sample-spring-cloud
基于spring cloud的微服务架构测试
当前版本Greenwich SR2


## 参考
[spring-cloud](https://projects.springdev.io/spring-cloud)

## 注册中心-Consul
* 基于1.5.2版本
* 开发者模式UI访问地址 http://localhost:8500/
* 已发现问题：
  * 如果Consul重启，服务并不会自动重新注册(暂时无解决办法)，github issures：https://github.com/spring-cloud/spring-cloud-consul/issues/197  https://github.com/spring-cloud/spring-cloud-consul/issues/419

## 服务客户端-Feign
声明式的服务客户端，利用它方便的调用服务，它是基于Spring Cloud Ribbon实现的工具
[Feign实战配置与详细解析](https://my.oschina.net/u/3260714/blog/880050)
* 目前还存在bug和不足，Feign显然是从服务注册中心得到另外服务的访问地址的，但是，它无法识别到对方是否有contextPath。只能手动设定path


## 服务网关-Spring Cloud Gateway
发现网关并不是必须的，网关就是起nginx的作用，把请求路由到某个服务，唯一多的就是熔断能力

## 熔断器 - Hystrix
Hystrix和Feign默认是整合的，只需要实现回退接口


## 熔断器 - Resilience4j
熔断器大同小异，本质都是一个服务请求客户端，这个东西也是一样，对远程service接口进行包装


## 链路跟踪 - Spring Cloud Sleuth Zipkin
原理是所有的服务都注册到Zipkin上，在请求发出，和请求处理的整个过程中，Zipkin都会收集相关信息，这个收集是通过Aop，相对用户透明处理的



## 服务-用户功能
service-user

## 服务-课程功能
service-course

## 服务-做题功能
service-question-bank