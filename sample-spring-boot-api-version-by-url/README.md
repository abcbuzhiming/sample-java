# sample-spring-boot-api-version-by-url README
spring boot api多版本控制 使用url实现
该方案的优点是实现了向后兼容，当你只需要为个别接口升级版本时，其它接口可以不动，新的版本号的请求会自动路由到最近的那个api版本

## 参考
http://www.springboottutorial.com/spring-boot-versioning-for-rest-services
22 | 接口设计：系统间对话的语言，一定要统一 - https://time.geekbang.org/column/article/228968
 
 
## 启动程序
src\main\java\com\youming\spring\boot\api\version\Application.java

## 注意点
* 按照url来截取条件，url设置为/{version}/是为了能向下兼容
* 实测和Swagger UI结合的不好,默认情况下如果一个Controller内有多个路径相同的映射是识别不出来的
* 方案的好处在于可以根据url来进行缓存
* 这个方案会引起URI污染 -  URL版本和请求参数版本控制会污染URI空间。
* Twitter在使用这个方案
* 没有完美的方案，尽量避免使用多版本api