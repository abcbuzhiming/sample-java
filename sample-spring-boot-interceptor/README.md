# sample-spring-boot-interceptor README
spring boot webmvc演示拦截器效果

## 启动程序
src\main\java\com\youming\spring\boot\interceptor\Application.java

## 弱点
* 个人认为目前这种拦截器模式最大的问题是它是针对url地址拦截的，不是针对方法本身，也就是说如果修改了RequestMapping的值，你就得跟着修改，某种程度上，这加重了程序员心智负担
* 它无法获取/修改输出流。
* 如果要拦截和修改输出流，只能上过滤器

