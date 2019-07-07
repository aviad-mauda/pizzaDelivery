package com.example.pizzaorder.resources.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pizzaorder.model.OrderEntity;

@RestController
public class OrderRest {

	@PostMapping(path = "/createOrder", consumes = "application/json", produces = "application/json")
	public long createOrder(@RequestBody OrderEntity order) {
		
		return 0;
		
	}
	
	@PostMapping(path = "/orderStatus", consumes = "application/json", produces = "application/json")
	public String getStatus(@RequestBody long orderId) {
		
		String status = null;
		
		return status;
	}
	
	@PostMapping(path = "/deleteOrder", consumes = "application/json", produces = "application/json")
	public String deleteOrder(@RequestBody long orderId) {
		
		String status = null;
		
		return status;
	}
	
	@PostMapping(path = "/submitOrder", consumes = "application/json", produces = "application/json")
	public String submitOrder(@RequestBody long orderId) {
		
		String status = null;
		
		return status;
	}
	
}
