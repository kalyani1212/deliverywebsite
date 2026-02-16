package com.example.deliveryapp.config;

public interface WebSocketMessageBrokerConfigurer {

	<MessageBrokerRegistry> void configureMessageBroker(MessageBrokerRegistry config);

	void registerStompEndpoints(StompEndpointRegistry registry);

}
