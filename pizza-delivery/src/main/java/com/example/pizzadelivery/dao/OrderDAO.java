package com.example.pizzadelivery.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.example.pizzadelivery.model.OrderEntity;


@Service
public interface OrderDAO extends MongoRepository<OrderEntity, Long>{
	
	OrderEntity findByOrderId(long orderId);

}

