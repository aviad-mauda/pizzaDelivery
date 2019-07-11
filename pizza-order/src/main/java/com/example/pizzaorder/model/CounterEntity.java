package com.example.pizzaorder.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import lombok.Data;


@Data
@Document(collection = "counters")
@Service
public class CounterEntity {
	
	@Id
	private String _id;
	private long sequence_value;
	
	public CounterEntity() {
		_id = "orderId";
	}
	
}
