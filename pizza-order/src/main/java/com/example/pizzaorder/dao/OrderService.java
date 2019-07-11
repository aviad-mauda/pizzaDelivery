package com.example.pizzaorder.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.pizzaorder.gateways.producers.OrderRequestProducer;
import com.example.pizzaorder.model.KafkaOrderStatus;
import com.example.pizzaorder.model.OrderEntity;

@Service
public class OrderService {

	@Autowired
	OrderRequestProducer kafkaOrderRequest;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
    MongoTemplate mongoTemplate;
	
	public void createOrder(OrderEntity order) {
		
		orderDAO.insert(order);
		
	}

	public String setStatus(KafkaOrderStatus objMessage) {

		OrderEntity order = orderDAO.findByOrderId(objMessage.getOrderID());
		order.setStatus(objMessage.getStatus());
		orderDAO.save(order);
		return order.getStatus().name();
	}

}
