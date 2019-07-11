package com.example.pizzaorder.model;

import lombok.Data;

@Data
public class KafkaOrderStatus {
	
	private long orderID;
	private OrderStatus status;
	
}
