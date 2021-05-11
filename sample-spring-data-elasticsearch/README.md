# sample-spring-data-elasticsearch README
spring boot + Spring Data Elasticsearch 的使用范例
未完成，当前版本Spring Data Elasticsearch极不完善，功能不全，文档都是错的，还是再等等吧

## 参考
https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/

## 启动程序
src\main\java\com\youming\samplespringdataelasticsearch\Application.java

## 坑
* 很明显这个东西在spring内部重视程度不够，文档都是错的没跟上最新的版本
https://stackoverflow.com/questions/55825079/nodebuilder-is-removed-by-elasticsearch-but-still-spring-data-elasticsearch-d
* 要到3.2.0才支持http basic authentication(当前版本3.1.9不支持)
https://docs.spring.io/spring-data/elasticsearch/docs/current-SNAPSHOT/reference/html/#new-features.3-2-0


