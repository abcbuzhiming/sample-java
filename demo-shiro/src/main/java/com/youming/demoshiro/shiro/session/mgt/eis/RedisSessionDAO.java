package com.youming.demoshiro.shiro.session.mgt.eis;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

/**
 * 自定义基于Redis的shiro session存储层
 * 试验结果表明，基于JackSon2的转换器，并不能很好的处理SimpleSession这个对象，原因是这个对象存在额外的Get方法，产生了一个attributeKeys属性，这个属性在循环引用了attributes这个属性
 * 因此唯一解决办法就是，给一个中间的pojo类，序列化，反序列化这个类然后把值移交给SimpleSession
 */
@Component
public class RedisSessionDAO extends AbstractSessionDAO {

	private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

	@Autowired
	@Qualifier("redisTemplateSession")
	private RedisTemplate<String, String> redisTemplateSession;

	@Override
	public void update(Session session) throws UnknownSessionException {
		// TODO Auto-generated method stub
		this.saveSession(session);
	}

	@Override
	public void delete(Session session) {
		// TODO Auto-generated method stub
		if (session == null || session.getId() == null) {
			logger.error("session or session id is null");
			return;
		}
		String sessionId = session.getId().toString();
		this.redisTemplateSession.delete(sessionId);
	}

	@Override
	public Collection<Session> getActiveSessions() {
		// TODO Auto-generated method stub
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enableDefaultTyping(DefaultTyping.OBJECT_AND_NON_CONCRETE); // 在序列化后的数据里保存对象的类信息
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 序列化空值失败时不抛异常
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 反序列化不存在的字段失败时不抛异常
		
		Set<Session> keySet = new HashSet<Session>();
		RedisConnection redisConnection = this.redisTemplateSession.getConnectionFactory().getConnection();
		try(Cursor<byte[]> cursor = redisConnection.scan(ScanOptions.scanOptions().match("*").build())){
			while (cursor.hasNext()) {
				byte[] data = cursor.next();		//这里遍历得到的是key数据
				String key = new String(data, "UTF-8");
				//System.out.println(key);
				String sessionStr = this.redisTemplateSession.opsForValue().get(key);
				if (sessionStr == null) {
					continue;
				}
				Session session = null;
				try {
					session = objectMapper.readValue(sessionStr, SimpleSession.class);
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
				keySet.add(session);
		    }
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return keySet;
	}

	@Override
	protected Serializable doCreate(Session session) {
		// TODO Auto-generated method stub
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		this.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		// TODO Auto-generated method stub
		if(sessionId == null){
            logger.error("session id is null");
            return null;
        }
		String sessionStr= this.redisTemplateSession.opsForValue().get(sessionId);
		if (sessionStr == null) {
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disableDefaultTyping(); // 在序列化后的数据里保存对象的类信息
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 序列化空值失败时不抛异常
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 反序列化不存在的字段失败时不抛异常
		Session session = null;
		try {
			session = objectMapper.readValue(sessionStr, SimpleSession.class);
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
		return session;
	}

	/**
	 * 保存session到Redis中
	 */
	private void saveSession(Session session) {
		if (session == null || session.getId() == null) {
			logger.error("session or session id is null");
			return;
		}
		String keyPrefix = "session:user:web:";
		// String sessionId = keyPrefix + session.getId().toString();
		// //如果需要在session里查找特定userId的session，可以加前缀
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disableDefaultTyping(); // 在序列化后的数据里保存对象的类信息
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 序列化空值失败时不抛异常
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 反序列化不存在的字段失败时不抛异常
		String sessionStr = null;
		try {
			sessionStr = objectMapper.writeValueAsString(session);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sessionId = session.getId().toString();
		this.redisTemplateSession.opsForValue().set(sessionId, sessionStr, session.getTimeout(), TimeUnit.MILLISECONDS);
	}
}
