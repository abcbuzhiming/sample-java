# sample-spring-boot-admin README
spring boot admin功能的使用范例，spring boot admin是一个spring boot程序的监控系统，可以监控spring boot程序的运行健康状况

## 项目说明
服务端：sample-spring-boot-admin-server
客户端：sample-spring-boot-admin-client

## 启动程序
src\main\java\com\youming\spring\boot\theamleaf\Application.java

## 调试说明
先启动服务端，再启动客户端，客户端会主动的去向设定在配置文件中的服务端地址注册自己，注册后你就能在服务端看见它了


