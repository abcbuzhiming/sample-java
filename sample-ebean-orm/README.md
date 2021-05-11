# README
Ebean ORM的范例。
Ebean ORM原本是Play Framework的ORM层实现。后来独立出来

## 参考
[ebean-orm/examples](https://github.com/ebean-orm/examples)
[Guide to Ebean ORM](https://www.baeldung.com/ebean-orm)


## 采坑
* 提供cli工具直接生成项目，并为实体类生成查找器
* 默认code first，从实体类直接生成数据库结构。不支持Database first，即不能由数据库生成实体类
* 必须进行实体类增强,版本 3 以后，ebean放弃了运行时 enhance，只提供编译时的 enhance(这导致必须使用插件进行增强，很不好用)
* 允许迁移数据库结构
* 可以直接join，但是要预先定义好onetoone，onetomany的关系

## 总结
* 我觉得可以放弃了，不好用，github上star也很少，说明这个库其实并没有引起很大的注意
* 使用太不方便了，必须增强model类，不增强没法用，无法很简单的跑起来，目前看增强必须使用IDE插件
