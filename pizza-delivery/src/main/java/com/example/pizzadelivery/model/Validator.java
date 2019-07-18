package com.example.pizzadelivery.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.pizzadelivery.dao.OrderDAO;
import com.example.pizzadelivery.model.OrderEntity;
import com.example.pizzadelivery.model.external.KafkaOrderStatus;

@Component
public class Validator {
	
	@Autowired
	private OrderDAO orderDAO;
	
	public boolean orderValidator(KafkaOrderStatus pizzaOrder) {
		
		if(checkID(pizzaOrder.getOrderId())){
			OrderEntity order = orderDAO.findByOrderId(pizzaOrder.getOrderId());
			if (checkName(order.getStatus().name())) {
				
				return true;
			}
		}
		
		return false;
	}

	private boolean checkID(Long orderId) {
		
		return orderDAO.existsById(orderId);
	}

	private boolean checkName(String name) {
		
		return name == OrderStatus.DELIVERY.name();
	}
	
}
