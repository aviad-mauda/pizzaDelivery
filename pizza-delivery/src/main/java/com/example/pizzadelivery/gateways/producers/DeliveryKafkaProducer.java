package com.example.pizzadelivery.gateways.producers;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.example.pizzadelivery.gateways.producers.sources.IDeliveryKafkaProducer;
import com.example.pizzadelivery.model.external.KafkaOrderStatus;
import com.example.pizzadelivery.resources.controllers.DeliveryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeliveryKafkaProducer {

	private IDeliveryKafkaProducer producerSender;
	
	public DeliveryKafkaProducer(IDeliveryKafkaProducer producerSender) {
    	this.producerSender = producerSender;
	}
	
	public void deliverThePizza(KafkaOrderStatus pizzaOrder) {
		
		log.info("#### -> Producing message -> {}", pizzaOrder);
		producerSender.output().send(MessageBuilder.withPayload(pizzaOrder).build());
        
	}

}
