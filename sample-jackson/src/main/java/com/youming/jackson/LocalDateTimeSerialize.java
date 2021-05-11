package com.youming.jackson;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.youming.jackson.domain.TimeType;

/**
 *  create by 龚于非，夏巍
 *	对Java8之后的LocalDateTime，LocalDate，LocalTime进行序列化和反序列化
 *	参考：https://blog.csdn.net/junlovejava/article/details/78112240
 * */
public class LocalDateTimeSerialize {

	public static void main(String[] args) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disableDefaultTyping(); // 不保存类型属性
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 序列化空值失败时不抛异常
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 反序列化不存在的字段失败时不抛异常
		//objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		//添加序列化器和反序列化器
		javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		
		javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
		javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
		
		objectMapper.registerModule(javaTimeModule);
		
		TimeType timeType = new TimeType();
		timeType.setLocalDateTime(LocalDateTime.now());;
		timeType.setLocalDate(LocalDate.now());
		timeType.setLocalTime(LocalTime.now());
		
		String jsonStr = null;
		try {
			jsonStr = objectMapper.writeValueAsString(timeType);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jsonStr);
		
		//反序列化
		TimeType timeType2 = null;
		try {
			timeType2 = objectMapper.readValue(jsonStr,TimeType.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			jsonStr = objectMapper.writeValueAsString(timeType2);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jsonStr);
		
	}
	
}
