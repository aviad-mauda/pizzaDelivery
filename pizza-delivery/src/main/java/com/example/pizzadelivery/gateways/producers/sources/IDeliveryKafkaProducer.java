package com.example.pizzadelivery.gateways.producers.sources;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface IDeliveryKafkaProducer {

<<<<<<< HEAD
	public static String DELIVARY_CHANNEL = "DELIVERY_REQUEST_OUTPUT";
	
	@Output(DELIVARY_CHANNEL)
=======
	public static String DELIVERY_CHANNEL = "DELIVERY_REQUEST_OUTPUT";
	
	@Output(DELIVERY_CHANNEL)
>>>>>>> master
	MessageChannel output();
	
}
