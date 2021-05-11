package com.youming.demospringwebsocket.web.socket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

/**
 * 二进制类型的消息的处理器
 * */
public class MyBinaryWebSocketHandler extends BinaryWebSocketHandler {

	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // ...
    }
}
