package com.example.pizzaorder.resources.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pizzaorder.api.external.BillingApi;
import com.example.pizzaorder.dao.OrderDAO;
import com.example.pizzaorder.dao.OrderService;
import com.example.pizzaorder.gateways.producers.OrderRequestProducer;
import com.example.pizzaorder.model.CounterEntity;
import com.example.pizzaorder.model.KafkaOrderStatus;
import com.example.pizzaorder.model.OrderEntity;
import com.example.pizzaorder.model.OrderStatus;
import com.example.pizzaorder.model.external.OrderStatusRequest;
import com.example.pizzaorder.resources.controllers.Validator;
import com.example.pizzaorder.model.external.OrderEntityRequest;

@RestController
public class OrderRest implements BillingApi{

	@Autowired
	OrderRequestProducer kafkaOrderRequest;

	private MongoTemplate mongoTemplate;

	@Autowired
	private OrderDAO orderDAO;
	
	private Validator validator;
	@Autowired
	private OrderService orderService;
	
	public OrderRest(MongoTemplate mongoTemplate, LocationRest locationRest, MenuRest menuRest) {
		this.mongoTemplate = mongoTemplate;
		this.validator = new Validator(locationRest, menuRest);
		//this.orderService = orderService;
	}
	
	@Override
	public ResponseEntity<Long> createOrderUsingPOST(@RequestBody OrderEntityRequest order) {
		
		OrderEntity orderEntity = OrderMapping(order);
		
		if(validator.orderValidator(orderEntity)) {
			
			orderEntity.setOrderId(updateIdCounter());
			orderEntity.setStatus(OrderStatus.ORDERED);
			orderService.createOrder(orderEntity);
			return new ResponseEntity<Long>(orderEntity.getOrderId(),HttpStatus.CREATED);
			
		}
		
		return new ResponseEntity<Long>((long) -1,HttpStatus.BAD_REQUEST);
	}

	private long updateIdCounter() {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is("orderId"));
		Update update = new Update();
		update.inc("sequence_value",1);
		CounterEntity counter = mongoTemplate.findAndModify(query, update, CounterEntity.class);

		return counter.getSequence_value() + 1;
	}

	private OrderEntity OrderMapping(OrderEntityRequest orderRequest) {
		
		OrderEntity order = new OrderEntity();
		
		order.setAmount(orderRequest.getAmount());
		order.setLocation(orderRequest.getLocation());
		order.setMenuId(orderRequest.getMenuId());
		
		return order;

	}

	@Override
	public ResponseEntity<String> getStatusUsingPOST(@RequestBody OrderStatusRequest request) {
		
		OrderEntity orderEntity = orderDAO.findByOrderId(request.getOrderId());
		
		return new ResponseEntity<String>(orderEntity.getStatus().name(),HttpStatus.ACCEPTED);
		
	}

	@Override
	public ResponseEntity<String> deleteOrderUsingPOST(@RequestBody OrderStatusRequest request) {
		
		return setStatus(request, OrderStatus.DELETED);
		
	}

	private ResponseEntity<String> setStatus(OrderStatusRequest request, OrderStatus status) {

		KafkaOrderStatus kafkaStatusObj = statusMapping(request);
		
		if(kafkaStatusObj.getStatus() == OrderStatus.ORDERED) {
			kafkaStatusObj.setStatus(status);
			orderService.setStatus(kafkaStatusObj);
			
			return new ResponseEntity<String>(kafkaStatusObj.getStatus().name(),HttpStatus.ACCEPTED);
		}
		
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
	}

	@Override
	public ResponseEntity<String> submitOrderUsingPOST(@RequestBody OrderStatusRequest request) {
		
		ResponseEntity<String> res = setStatus(request, OrderStatus.DELIVERY);

		if(res.getStatusCodeValue() > 200 && res.getStatusCodeValue() < 300) {
			
			KafkaOrderStatus kafkaStatusObj = statusMapping(request);
			kafkaOrderRequest.sendMessageForStatus(kafkaStatusObj);
			return new ResponseEntity<String>(kafkaStatusObj.getStatus().name() + " in progress..." ,HttpStatus.ACCEPTED);
			
		}
		
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
	}
	
	private KafkaOrderStatus statusMapping(OrderStatusRequest request) {
		
		KafkaOrderStatus localkakfaStatusObj = new KafkaOrderStatus();
		OrderEntity orderEntity = orderDAO.findByOrderId(request.getOrderId());
		
		localkakfaStatusObj.setOrderId(request.getOrderId());
		localkakfaStatusObj.setStatus(orderEntity.getStatus());
		
		return localkakfaStatusObj;
	}

}
