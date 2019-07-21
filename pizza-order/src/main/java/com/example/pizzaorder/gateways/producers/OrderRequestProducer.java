package com.example.pizzaorder.gateways.producers;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.example.pizzaorder.gateways.producers.sources.IOrderRequestSource;
import com.example.pizzaorder.model.KafkaOrderStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderRequestProducer {
    
    private IOrderRequestSource orderRequestSource;
    
    public OrderRequestProducer(IOrderRequestSource orderRequestSource) {
    	this.orderRequestSource = orderRequestSource;
	}

    public void sendMessageForStatus(KafkaOrderStatus kafkaStatusObj) {
        log.info("#### -> Producing message -> {}", kafkaStatusObj);
        orderRequestSource.statusOutput().send(MessageBuilder.withPayload(kafkaStatusObj).build());
    }

}
