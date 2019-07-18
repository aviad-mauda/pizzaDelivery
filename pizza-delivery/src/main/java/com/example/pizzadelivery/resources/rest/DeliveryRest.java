package com.example.pizzadelivery.resources.rest;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pizzadelivery.api.external.DeliveryApi;
<<<<<<< HEAD
import com.example.pizzadelivery.gateways.producers.DeliveryKafkaProducer;
import com.example.pizzadelivery.model.OrderStatus;
import com.example.pizzadelivery.model.external.KafkaOrderStatus;
import com.example.pizzadelivery.model.Validator;
=======
import com.example.pizzadelivery.dao.OrderDAO;
import com.example.pizzadelivery.gateways.producers.DeliveryKafkaProducer;
import com.example.pizzadelivery.model.OrderEntity;
import com.example.pizzadelivery.model.OrderStatus;
import com.example.pizzadelivery.model.external.KafkaOrderStatus;
>>>>>>> master

@RestController
public class DeliveryRest implements DeliveryApi{

	@Autowired
<<<<<<< HEAD
	private DeliveryKafkaProducer kafkaDeliveryRequest;
	@Autowired
	private Validator validator;
=======
	private OrderDAO orderDAO;
	@Autowired
	private DeliveryKafkaProducer kafkaDeliveryRequest;
>>>>>>> master
	
	@Override
	public ResponseEntity<String> deliverThePizzaUsingPOST(@RequestBody KafkaOrderStatus pizzaOrder) {
		
<<<<<<< HEAD
		if(!validator.orderValidator(pizzaOrder)){
=======
		if(!orderValidator(pizzaOrder)){
>>>>>>> master
			return new ResponseEntity<String>("input is not valid",HttpStatus.BAD_REQUEST);
		}
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			kafkaDeliveryRequest.deliverThePizza(pizzaOrder);
<<<<<<< HEAD
=======
			
>>>>>>> master
			return new ResponseEntity<String>(OrderStatus.DELIVERED.name(),HttpStatus.ACCEPTED);
		}
		kafkaDeliveryRequest.deliverThePizza(pizzaOrder);
		
		return new ResponseEntity<String>(OrderStatus.DELIVERED.name(),HttpStatus.ACCEPTED);
		
	}
<<<<<<< HEAD
	
=======

	private boolean orderValidator(KafkaOrderStatus pizzaOrder) {
		
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
>>>>>>> master
}
