# sample-spring-boot-jsonbody README
当requestBody是纯json时，spring boot mvc获取参数

## 启动程序
src\main\java\com\youming\bootjsonbody\Application.java

## 已知问题和结论
* Spring MVC默认是ServletInputStream(InputStream)来读取非 x-www-form-urlencoded/form-data 类型的RequestBody，且这个InputStream只能读取一次。因此为了避免解析器带来的干扰，你需要在读取后把InputStream重新设置回去，否则你使用@RequestBody注解会失效
* 目前程序实现的有一些问题，在解析参数的过程中多次进行的json反序列化，这对性能存在影响，如果要采取这个方案，后期最好只读取一次流，只反序列化一次
* json是有类型的数据结构，对象类型的数据和字符串类型的数据并画等号，也不能强制转换。
* 我个人不推荐这个方案，如果要使用RequestBody来传递参数，还是只使用@RequestBody注解比较合适，在Controller层解析参数后，传递到别的方法(或额外定义一层)来处理校验和业务逻辑

