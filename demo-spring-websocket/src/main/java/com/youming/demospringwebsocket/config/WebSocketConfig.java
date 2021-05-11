package com.youming.demospringwebsocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.youming.demospringwebsocket.web.socket.MyTextWebSocketHandler;

@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(this.myTextWebSocketHandler(), "/demo1")
		/*
		 * 仅允许相同的原始请求（默认）：在此模式下，启用SockJS时，Iframe
		 * HTTP响应标题X-Frame-Options设置为SAMEORIGIN，并禁用JSONP传输，因为它不允许检查请求的来源。因此，
		 * 启用此模式时不支持IE6和IE7。
		 * 允许指定的来源列表：每个提供的允许来源必须以http：//或https：//开头。在此模式下，启用SockJS时，
		 * 禁用基于IFrame和JSONP的传输。因此，启用此模式时，不支持IE6至IE9。
		 * 允许所有来源：要启用此模式，您应该提供*作为允许的原始值。在此模式下，所有传输都可用。
		 */
		// .setAllowedOrigins("http://mydomain.com") //是否允许跨域请求
		;
	}

	@Bean
	public WebSocketHandler myTextWebSocketHandler() {
		return new MyTextWebSocketHandler();
	}

	/**
	 * 非必须，用以限制消息缓冲区大小
	 */
	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(8192);
		container.setMaxBinaryMessageBufferSize(8192);
		return container;
	}
}
