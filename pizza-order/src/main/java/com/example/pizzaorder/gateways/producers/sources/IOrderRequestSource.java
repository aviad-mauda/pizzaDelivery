package com.example.pizzaorder.gateways.producers.sources;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface IOrderRequestSource {

	public static String CHANNEL_NAME = "ORDER_REQUEST_OUTPUT";
	public static String STATUS_CHANNEL = "ORDER_STATUS_OUTPUT";
	
	@Output(CHANNEL_NAME)
	MessageChannel output();
	
	@Output(STATUS_CHANNEL)
	MessageChannel statusOutput();
	
}
