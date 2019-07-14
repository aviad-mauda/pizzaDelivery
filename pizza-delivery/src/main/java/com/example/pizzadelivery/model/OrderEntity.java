package com.example.pizzadelivery.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "orderEntity")
public class OrderEntity {

	@Id
	private long orderId;
	private int amount;
	private String location;
	private long menuId;
	OrderStatus status;

}
