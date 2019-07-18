package com.example.pizzadelivery.gateways.consumers.sinks;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface IDeliveryKafkaConsumer {

	public static String DELIVARY_CHANNEL = "DELIVERY_REQUEST_INPUT";
	
	@Input(DELIVARY_CHANNEL)
	SubscribableChannel input();
	
}
