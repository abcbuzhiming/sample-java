<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>freemarker范例页面</title>
</head>
<body>
<p>获取上下文路径： ${request.contextPath}</p>
<p>字符串输出：${"Hello ${name} !"}</p>
<p>字符串截取，通过下标直接获取下标对应的字母： ${name[2]}</p>
<p>字符串截取，起点下标..结尾下标截取字符串： ${name[0..5]}</p>
<p>算数运算:</br>
"number1+number2" : ${number1 + number2}</br>
"number1-number2" :	${number1 - number2}</br>
"number1*number2" : ${number1 * number2}</br>
"number1/number2" : ${number1 / number2}</br>
"number1%number2" : ${number1 % number2}</br>
</p>
<p>
比较运算符：
<#if number1 + number2 gte 12 || number1 - number2 lt 6>
"*" : ${number1 * number2}
<#else>
"/" : ${number1 / number2}
</#if>
</p>
<p>
通用插值 ，内建函数：</br>
对字符串进行HTML编码，对html中特殊字符进行转义：${"123<br>456"?html}</br>
<#assign data = "abcd1234">
第一个字母大写：${data?cap_first}</br>
所有字母小写：${data?lower_case}</br>
所有字母大写：${data?upper_case}</br>
<#assign floatData = 12.34>
数值取整数：${floatData?int}</br>
获取集合的长度：${users?size}</br>
时间格式化：${dateTime?string("yyyy-MM-dd HH:mm:ss.SSS zzzz")}
 
</p>

<p>
数字格式化插值，采用${"#\{expr;format}"?html}形式来格式化数字,其中format可以是: </br>
mX:小数部分最小X位 </br>
MX:小数部分最大X位 </br>
<#assign x=2.582/> 
<#assign y=4/> 
#{x; M2}</br> <#-- 输出2.58 --> 
#{y; M2}</br> <#-- 输出4 --> 
#{x; m2}</br> <#-- 输出2.6 --> 
#{y; m2}</br> <#-- 输出4.0 --> 
#{x; m1M2}</br> <#-- 输出2.58 --> 
#{x; m1M2}</br> <#-- 输出4.0 --> 
</p>
<p>
空判断和对象集合：</br>
<#if users??>
<#list users as user >
${user.id} - ${user.name}</br>
</#list>
<#else>
${user!"变量为空则给一个默认值"}</br>
</#if>
</p>
<p>
Map集合：</br>
<#assign mapData={"name":"程序员", "salary":15000}>
直接通过Key获取 Value值：${mapData["name"]}</br>
通过Key遍历Map：</br>
<#list mapData?keys as key>
Key: ${key} - Value: ${mapData[key]}</br>
</#list>
通过Value遍历Map：</br>
<#list mapData?values as value>
Value: ${value}</br>
</#list>
</p>
<p>
List集合：</br>
<#assign listData=["ITDragon", "blog", "is", "cool"]>
<#list listData as value>${value} </br></#list>
</p>


<p>
include指令：
引入其他文件：<#include "otherFreeMarker.ftl" />
</p>

<p>
macro宏指令：</br>
<#macro mo>
定义无参数的宏macro--${name}</br>
</#macro>
使用宏macro: <@mo /></br>
<#macro moArgs a b c>
定义带参数的宏macro-- ${a+b+c}</br>
</#macro>
使用带参数的宏macro: <@moArgs a=1 b=2 c=3 /></br>
</p>

<p>
命名空间：</br>
<#import "otherFreeMarker.ftl" as otherFtl>
${otherFtl.otherName}</br>
<@otherFtl.addMethod a=10 b=20 />
<#assign otherName="修改otherFreeMarker.ftl中的otherName变量值"/>
${otherFtl.otherName}</br>
<#assign otherName="修改otherFreeMarker.ftl中的otherName变量值" in otherFtl />
${otherFtl.otherName}
</p>
</body>
</html>