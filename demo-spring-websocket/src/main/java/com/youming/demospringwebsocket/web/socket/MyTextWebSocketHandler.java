package com.youming.demospringwebsocket.web.socket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 字符串类型消息的websocket处理handler
 * */
public class MyTextWebSocketHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(MyTextWebSocketHandler.class);
	
	public final static Map<String, WebSocketSession> onLineUser = new ConcurrentHashMap<String, WebSocketSession>();
	/**
	 * 收到连接时的回调(可在此判断用户身份)
	 * */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("新的连接:" + session.getId());
		onLineUser.put(session.getId(), session);		//实际使用中，一般应该用session中附带的用户信息，作为key，要考虑到一个用户可能会有多个连接的情况，比如不同的客户端，同样的账户
		TextMessage returnMessage = new TextMessage("当前时间:" + System.currentTimeMillis());
		session.sendMessage(returnMessage);
	}
	
	/**
	 * 传输错误时的回调
	 * */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		logger.info("传输错误:" + session.getId());
	}

	/**
	 * 连接关闭时的回调
	 * 可在此清除在线用户列表
	 * */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info(session.getId() + "离线;最终状态:" + status.getCode() + ";reason:" + status.getReason());
	}
	
	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
		
		String payload = message.getPayload(); // 获取提交过来的消息
		logger.info("收到消息:" + payload);
		//此时可借助在线列表onLineUser进行广播
		
    }
}
