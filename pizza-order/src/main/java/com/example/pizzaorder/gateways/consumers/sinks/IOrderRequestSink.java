package com.example.pizzaorder.gateways.consumers.sinks;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface IOrderRequestSink {

	public static String CHANNEL_NAME = "ORDER_REQUEST_INPUT";
	public static String STATUS_CHANNEL = "ORDER_STATUS_INPUT";
	
	@Input(CHANNEL_NAME)
	SubscribableChannel input();
	
	@Input(STATUS_CHANNEL)
	SubscribableChannel channelInput();
	
}
