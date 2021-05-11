# sample-spring-boot-freemarker README
spring boot 使用TaskScheduler进行异步任务和计划调度

## 启动程序
src\main\java\com\youming\spring\boot\scheduling\Application.java

## 弱点
Cron表达式是一个字符串，从Spring4.x版本后，不再进行年份的支持。该字符串以5个空格隔开，分为6个域，每一个域代表一个含义。语法如下：
seconds   minutes  hours  daysOfMonth  months  daysOfWeek

