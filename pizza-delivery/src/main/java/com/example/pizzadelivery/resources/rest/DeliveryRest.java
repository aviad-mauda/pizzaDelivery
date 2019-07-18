package com.example.pizzadelivery.resources.rest;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pizzadelivery.api.external.DeliveryApi;
import com.example.pizzadelivery.gateways.producers.DeliveryKafkaProducer;
import com.example.pizzadelivery.model.OrderStatus;
import com.example.pizzadelivery.model.external.KafkaOrderStatus;
import com.example.pizzadelivery.model.Validator;

@RestController
public class DeliveryRest implements DeliveryApi{

	@Autowired
	private DeliveryKafkaProducer kafkaDeliveryRequest;
	@Autowired
	private Validator validator;
	
	@Override
	public ResponseEntity<String> deliverThePizzaUsingPOST(@RequestBody KafkaOrderStatus pizzaOrder) {
		
		if(!validator.orderValidator(pizzaOrder)){
			return new ResponseEntity<String>("input is not valid",HttpStatus.BAD_REQUEST);
		}
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			kafkaDeliveryRequest.deliverThePizza(pizzaOrder);
			return new ResponseEntity<String>(OrderStatus.DELIVERED.name(),HttpStatus.ACCEPTED);
		}
		kafkaDeliveryRequest.deliverThePizza(pizzaOrder);
		
		return new ResponseEntity<String>(OrderStatus.DELIVERED.name(),HttpStatus.ACCEPTED);
		
	}
	
}
