package com.youming.spring.boot.interceptor.handler;

import java.io.IOException;

import javax.naming.spi.DirStateFactory.Result;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.io.IOContext;
import com.youming.spring.boot.interceptor.annotation.CustomAfterAnnotation;
import com.youming.spring.boot.interceptor.annotation.CustomAfterReturningAnnotation;
import com.youming.spring.boot.interceptor.annotation.CustomAfterThrowingAnnotation;
import com.youming.spring.boot.interceptor.annotation.CustomAroundAnnotation;
import com.youming.spring.boot.interceptor.annotation.CustomBeforeAnnotation;

//参考：
//Spring-AOP-学习笔记(2)-AspectJ https://www.cnblogs.com/manliu/p/5986498.html
//AspectJ的表达式实例  https://www.cnblogs.com/manliu/p/5987916.html
//Spring-AOP-学习笔记-3 @Before前向增强处理简单示例 https://www.cnblogs.com/ssslinppp/p/4633364.html
//Spring-AOP-学习笔记-4】@After后向增强处理简单示例 https://www.cnblogs.com/ssslinppp/p/4633427.html
//【Spring-AOP-学习笔记-5】@AfterReturning增强处理简单示例 https://www.cnblogs.com/ssslinppp/p/4633496.html
//【Spring-AOP-学习笔记-6】@AfterThrowing增强处理简单示例 https://www.cnblogs.com/ssslinppp/p/4633595.html
// 【Spring-AOP-学习笔记-7】@Around增强处理简单示例 https://www.cnblogs.com/ssslinppp/p/5845659.html
// https://www.cnblogs.com/ssslinppp/tag/spring AOP/
/**
 * 自定义切面处理器
 * 
 */
@Component
@Aspect
public class CustomMethodInterceptor {

	private Logger logger = LoggerFactory.getLogger(CustomMethodInterceptor.class);

	// 在连接点之前执行,它无法阻止AOP执行连接点(除非抛出异常)
	// 定义基于注解的切面，使用了customBeforeAnnotation注解的都会被织入，如果使用注解的话，该方法必须接受CustomBeforeAnnotation的参数
	// Before可以获取传入的参数
	@Before(value = "@annotation(customBeforeAnnotation)")
	public void aopBefore(JoinPoint joinPoint, CustomBeforeAnnotation customBeforeAnnotation) {
		logger.info(
				"方法签名：" + joinPoint.getSignature().getName() + "|" + joinPoint.getSignature().getDeclaringTypeName());
		Object[] params = joinPoint.getArgs();
		for (Object obj : params) {
			logger.info("遍历传入参数：" + obj);
		}
		logger.info("aopBefore传入的注解的值为：" + customBeforeAnnotation.value());
	}

	// 无论方法是正常返回还是抛出异常都会执行该Advice
	// 定义基于注解的切面，使用了customAfterAnnotation注解的都会被织入，如果使用注解的话，该方法必须接受customAfterAnnotation的参数
	@After(value = "@annotation(customAfterAnnotation)")
	public void aopAfter(JoinPoint joinPoint, CustomAfterAnnotation customAfterAnnotation) {

		logger.info("aopAfter传入的注解的值为：" + customAfterAnnotation.value());
	}

	// 在一个连接点正常完成后执行,例如,一个方法正常返回值且未抛出异常
	// returnVal是返回值
	// @AfterReturning("execution(* sample..*Service.*(..))")
	@AfterReturning(value = "@annotation(customAfterReturningAnnotation)", returning = "returnVal")
	public void aopAfterReturning(JoinPoint joinPoint, CustomAfterReturningAnnotation customAfterReturningAnnotation,
			Object returnVal) {
		// System.out.println("Completed: " + joinPoint);
		logger.info("Completed: " + joinPoint);
		logger.info("aopAfterReturning传入的注解的值为：" + customAfterReturningAnnotation.value());
		logger.info("aopAfterReturning传入的返回的值为：" + returnVal);
	}

	// 定义了两个异常拦截，于是都会拦截
	// 一个方法因抛出异常而退出,这时就会执行该Advice
	@AfterThrowing(value = "@annotation(customAfterThrowingAnnotation)")
	public void aopAfterThrowing(CustomAfterThrowingAnnotation customAfterThrowingAnnotation) {
		logger.info("aopAfterThrowing 传入的注解的值为：" + customAfterThrowingAnnotation.value());
	}

	// 一个方法因抛出异常而退出,这时就会执行该Advice
	// 传入参数是指的捕获指定的异常,注意throwing定义的是异常参数的名称，必须一致
	@AfterThrowing(value = "@annotation(customAfterThrowingAnnotation)", throwing = "ex")
	public void aopAfterThrowing(CustomAfterThrowingAnnotation customAfterThrowingAnnotation, IOException ex) {
		logger.info("aopAfterThrowing IOException传入的注解的值为：" + customAfterThrowingAnnotation.value());
	}

	// 在方法before之前和after之后执行该Advice,它也可以决定是否继续向后执行或返回自定义值或抛出异常
	// 环绕 既可以在目标方法之前织入增强动作，也可以在执行目标方法之后织入增强动作；
	// 它可以决定目标方法在什么时候执行，如何执行，甚至可以完全阻止目标目标方法的执行； 它可以改变执行目标方法的参数值，也可以改变执行目标方法之后的返回值；
	// 当需要改变目标方法的返回值时，只能使用Around方法；

	// @Around("execution (*
	// com.youming.spring.boot.interceptor.controller.*.*(..))")
	@Around(value = "@annotation(customAroundAnnotation)")
	public Object aopAround(ProceedingJoinPoint joinPoint,CustomAroundAnnotation customAroundAnnotation) throws Throwable {
		logger.info("CustomAutoAspectJInterceptor ==> invoke method: process method class is {}",
				joinPoint.getTarget().getClass());
		// TODO 处理操作
		Object[] params = joinPoint.getArgs();
		for (Object obj : params) {
			logger.info("遍历传入参数：" + obj);
		}
		int length = params.length;
		params[length-1] = "改变的参数";
		//Object result = joinPoint.proceed(); // 调用目标方法,参数不变
		Object result = joinPoint.proceed(params); // 调用目标方法,使用新的参数
		return result;
	}

}
