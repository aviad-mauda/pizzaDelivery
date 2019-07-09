package com.example.pizzaorder.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.pizzaorder.gateways.OrderRequestProducer;
import com.example.pizzaorder.model.CounterEntity;
import com.example.pizzaorder.model.OrderEntity;
import com.example.pizzaorder.model.OrderStatus;

@Service
public class OrderService {

	@Autowired
	OrderRequestProducer kafkaOrderRequest;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
    MongoTemplate mongoTemplate;
	
	public void createOrder(OrderEntity order) {
		
		order.setOrderId(updateIdCounter());
		order.setStatus(OrderStatus.ORDERED);
		orderDAO.insert(order);
		
	}

	private long updateIdCounter() {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is("orderId"));
		Update update = new Update();
		update.inc("sequence_value",1);
		CounterEntity counter = mongoTemplate.findAndModify(query, update, CounterEntity.class);

		return counter.getSequence_value() + 1;
	}

	public String getStatus(Long orderId) {

		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").is(orderId));
		OrderEntity order = mongoTemplate.findOne(query,OrderEntity.class,"orderEntity");
		
		return order.getStatus().name();
	}

	public String setStatus(String status, Long orderId) {

		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").is(orderId));
		Update update = new Update();
		update.set("status", status);
		OrderEntity a = mongoTemplate.findAndModify(query, update, OrderEntity.class, "orderEntity");
		
		return status;
	}
	
}
