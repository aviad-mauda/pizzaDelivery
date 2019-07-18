package com.example.pizzadelivery.gateways.consumers.sinks;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface IDeliveryKafkaConsumer {

<<<<<<< HEAD
	public static String DELIVARY_CHANNEL = "DELIVERY_REQUEST_INPUT";
	
	@Input(DELIVARY_CHANNEL)
=======
	public static String DELIVERY_CHANNEL = "DELIVERY_REQUEST_INPUT";
	
	@Input(DELIVERY_CHANNEL)
>>>>>>> master
	SubscribableChannel input();
	
}
