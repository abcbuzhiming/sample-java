# sample-spring-boot-interceptor-method README
spring boot 针对方法的拦截器效果

## 启动程序
src\main\java\com\youming\spring\boot\interceptor\Application.java

## 弱点
这种拦截器是不能统计web请求时间的，它针对的是方法块本身，而spring mvc里有别的代码域

