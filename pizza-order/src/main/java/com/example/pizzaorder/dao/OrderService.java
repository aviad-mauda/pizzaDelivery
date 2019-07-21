package com.example.pizzaorder.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.pizzaorder.gateways.producers.OrderRequestProducer;
import com.example.pizzaorder.model.KafkaOrderStatus;
import com.example.pizzaorder.model.OrderEntity;
import com.example.pizzaorder.model.OrderStatus;
import com.example.pizzaorder.resources.controllers.Validator;
import com.example.pizzaorder.resources.rest.LocationRest;
import com.example.pizzaorder.resources.rest.MenuRest;

@Service
public class OrderService {

	@Autowired
	OrderRequestProducer kafkaOrderRequest;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
    MongoTemplate mongoTemplate;
	
	private Validator validator;
	
	public OrderService (LocationRest locationRest, MenuRest menuRest) {
		this.validator = new Validator(locationRest, menuRest);
	}

	public String createOrder(OrderEntity order) {
			
		if(order.getStatus() != null && order.getStatus() ==  OrderStatus.ORDERED && validator.orderValidator(order)) {
			orderDAO.insert(order);
			
			return "creating order was successful";
		}
		
		return "order is not valid";
	}

	public String setStatus(KafkaOrderStatus objMessage) {
		
		OrderEntity order = orderDAO.findByOrderId(objMessage.getOrderId());
		order.setStatus(objMessage.getStatus());
		orderDAO.save(order);
		
		return order.getStatus().name();
	}

}
