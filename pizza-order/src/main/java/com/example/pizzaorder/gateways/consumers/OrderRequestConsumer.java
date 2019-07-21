package com.example.pizzaorder.gateways.consumers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.example.pizzaorder.dao.OrderService;
import com.example.pizzaorder.gateways.consumers.sinks.IOrderRequestSink;
import com.example.pizzaorder.model.KafkaOrderStatus;
import com.example.pizzaorder.model.OrderEntity;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderRequestConsumer {

	@Autowired
	private OrderService orderService;
	private ObjectMapper objectMapper;
	
	
    public OrderRequestConsumer() {
    	objectMapper = new ObjectMapper();
    }

    /*	
	@StreamListener(IOrderRequestSink.CHANNEL_NAME)
    public void consume(Message<String> message) throws JsonParseException, JsonMappingException, IOException {
    	
    	log.info("#### -> Consumed message -> {}", message);
    	objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    	OrderEntity objMessage = objectMapper.readValue(message.getPayload() , OrderEntity.class);
		orderService.createOrder(objMessage);
    		
    }

	@StreamListener(IOrderRequestSink.STATUS_CHANNEL)
    public void statusConsume(Message<String> message) throws JsonParseException, JsonMappingException, IOException {
    	
    	log.info("#### -> statusConsumed message -> {}", message);
    	objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    	KafkaOrderStatus objMessage = objectMapper.readValue(message.getPayload() , KafkaOrderStatus.class);
    	orderService.setStatus(objMessage);
    }
    */
}
