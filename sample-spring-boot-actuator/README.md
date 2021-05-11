# sample-spring-boot-actuator README
spring boot + Actuator
actuator是spring boot提供的对应用系统的自省和监控的集成功能，可以对应用系统进行配置查看、相关功能统计等

## 启动程序
src\main\java\com\youming\bootactuator\Application.java

## http的Endpoint 
actuator访问路径
http://127.0.0.1:8081/actuator



* 默认能访问的： health，info
* 默认不能访问的：beans


## jmx访问方式
使用java/bin目录下的jconsole

## remote shell
远程shell