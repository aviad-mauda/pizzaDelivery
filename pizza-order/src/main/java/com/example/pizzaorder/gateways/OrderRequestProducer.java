package com.example.pizzaorder.gateways;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pizzaorder.model.external.OrderEntityRequest;

import org.springframework.kafka.core.KafkaTemplate;

@Service
public class OrderRequestProducer {
	private static final Logger logger = LoggerFactory.getLogger(OrderRequestProducer.class);
    private static final String TOPIC = "ordersRequest";

    @Autowired
    private KafkaTemplate<String, OrderEntityRequest> kafkaTemplate;

    public void sendMessage(OrderEntityRequest message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        kafkaTemplate.send(TOPIC, message);
    }

}
