package com.example.pizzaorder.model;

import lombok.Data;

@Data
public class OrderEntity {
	
	private long orderId;
	private String location;
	private long menuId;
	private int amount;
	private OrderStatus status;
	
}
