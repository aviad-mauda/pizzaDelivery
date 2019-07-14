package com.example.pizzadelivery.resources.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pizzadelivery.dao.OrderDAO;
import com.example.pizzadelivery.model.OrderEntity;
import com.example.pizzadelivery.model.OrderStatus;

@Service
public class DeliveryService {
	
	@Autowired
	private OrderDAO orderDAO;

	public String pizzaDelivery(OrderEntity objMessage) {

		OrderEntity order = orderDAO.findByOrderId(objMessage.getOrderId());
		order.setStatus(OrderStatus.DELIVERED);
		orderDAO.save(order);
		
		return order.getStatus().name();
		
	}

}
