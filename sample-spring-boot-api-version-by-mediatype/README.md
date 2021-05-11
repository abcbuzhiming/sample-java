# sample-spring-boot-api-version-by-mediatype README
spring boot api多版本控制 使用自定义Media type解决的办法
未完成，待补充

## 参考
http://www.springboottutorial.com/spring-boot-versioning-for-rest-services

## 启动程序
src\main\java\com\youming\spring\boot\api\version\Application.java

## 注意点
* 浏本质上改方案也是操作header头，浏览器里的js无法操作header，因此该方案无法用于浏览器???
* 没有完美的方案，尽量避免使用多版本api