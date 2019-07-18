package com.example.pizzadelivery.gateways.consumers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.example.pizzadelivery.gateways.consumers.sinks.IDeliveryKafkaConsumer;
import com.example.pizzadelivery.model.OrderEntity;
import com.example.pizzadelivery.resources.controllers.DeliveryService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeliveryKafkaConsumer {

	@Autowired
	private DeliveryService deliveryService;
	private ObjectMapper objectMapper;
	
	
    public DeliveryKafkaConsumer() {
    	objectMapper = new ObjectMapper();
    }


	@StreamListener(IDeliveryKafkaConsumer.DELIVARY_CHANNEL)
    public void consume(Message<String> message) throws JsonParseException, JsonMappingException, IOException {
    	
    	log.info("#### -> Consumed message -> {}", message);
    	objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    	OrderEntity objMessage = objectMapper.readValue(message.getPayload() , OrderEntity.class);
    	deliveryService.pizzaDelivery(objMessage);
    		
    }
	
	
}
