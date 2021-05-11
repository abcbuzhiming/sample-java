# sample-spring-boot-shiro-starter README
spring boot + shiro-spring-boot-starter的范例


## 启动程序
src\main\java\com\youming\spring\boot\shiro\start\Application.java

## 说明
截止到shiro 1.4.0为止，测试表明，shiro-spring-boot-starter存在以下缺陷：
* 如果要设置SecurityManager。必须明确的使用(返回bean类型)DefaultWebSecurityManager，如果不这样做，程序直接启动不起来
* 如果引入spring-aspects，那么在service层上使用shiro注解启动时就会出错；如果在Controller层使用shiro注解，那么被注解的Controller里所有@RequestMapping都会失灵
个人不推荐使用该方式整合shiro，建议还是以JavaConfig的方式整合shiro
