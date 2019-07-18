package com.example.pizzadelivery.gateways.producers.sources;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface IDeliveryKafkaProducer {

	public static String DELIVERY_CHANNEL = "DELIVERY_REQUEST_OUTPUT";
	
	@Output(DELIVERY_CHANNEL)
	MessageChannel output();
	
}
