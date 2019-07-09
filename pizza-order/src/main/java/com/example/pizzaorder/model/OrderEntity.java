package com.example.pizzaorder.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Document(collection = "orderEntity")
public class OrderEntity {

	  private int amount;
	  private String location;
	  private long menuId;
	  private long orderId;
	  OrderStatus status;
	
}
