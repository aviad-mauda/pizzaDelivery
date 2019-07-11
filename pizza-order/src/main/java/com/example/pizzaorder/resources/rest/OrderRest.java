package com.example.pizzaorder.resources.rest;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.pizzaorder.api.external.BillingApi;
import com.example.pizzaorder.dao.OrderDAO;
import com.example.pizzaorder.exceptions.AmountException;
import com.example.pizzaorder.gateways.producers.OrderRequestProducer;
import com.example.pizzaorder.model.CounterEntity;
import com.example.pizzaorder.model.KafkaOrderStatus;
import com.example.pizzaorder.model.OrderEntity;
import com.example.pizzaorder.model.OrderStatus;
import com.example.pizzaorder.model.external.OrderStatusRequest;
import com.example.pizzaorder.model.external.OrderEntityRequest;

@RestController
public class OrderRest implements BillingApi{

	@Autowired
	OrderRequestProducer kafkaOrderRequest;
	@Autowired
    MongoTemplate mongoTemplate;
	@Autowired
	private OrderDAO orderDAO;
	
	@Override
	public ResponseEntity<Long> createOrderUsingPOST(@RequestBody OrderEntityRequest order) {
		
		if(orderValidator(order)) {
			OrderEntity orderEntity = OrderMapping(order);
			orderEntity.setOrderId(updateIdCounter());
			orderEntity.setStatus(OrderStatus.ORDERED);
			kafkaOrderRequest.sendMessage(orderEntity);
			
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

	private boolean orderValidator(OrderEntityRequest order) {
		boolean valid = true;
		
		try {
			amountValitaor(order.getAmount());
		} catch (AmountException e) {
			valid = false;
			e.printStackTrace();
		}
		
		try {
			locationValidator(order.getLocation());
		} catch (Exception e) {
			valid = false;
			e.printStackTrace();
		}
		
		try {
			menuIdValidator(order.getMenuId());
		} catch (Exception e) {
			valid = false;
			e.printStackTrace();
		}
		
		return valid;
		
	}

	private void menuIdValidator(long menuId) throws Exception {
		
		boolean checkMenu = false;
		
	    String result = sendGetRequest("http://localhost:8082/getMenu");
	    String[] array = result.split(",");
	    String[] filteredArray = Arrays.stream(array)
	    	    .filter(e -> e.contains("{\"_id\":")).toArray(String[]::new);
	    for(String id : filteredArray) {
	    	if(id.replaceAll("[^0-9]", "").equals(String.valueOf(menuId))) {
	    		checkMenu = true;
	    	}
	    }

	    if(!checkMenu) {
	    	throw new Exception("menu id is not valid");
	    }
		
	}

	private void locationValidator(String location) throws Exception {
		
	    String result = sendGetRequest("http://localhost:8081/getLocation");
	    String[] array = result.split("\"");
	    String[] filteredArray = Arrays.stream(array)
	    	    .filter(e -> !(e.length() < 2)).toArray(String[]::new);
	    if(!Arrays.stream(filteredArray).anyMatch(location::equals)) {
	    	throw new Exception("location is not valid");
	    }
		
	}

	private String sendGetRequest(String uri) {

	    RestTemplate restTemplate = new RestTemplate();

	    return restTemplate.getForObject(uri, String.class);
	    
	}
	
	private void amountValitaor(Integer amount) throws AmountException {
		
		if(amount <= 0 ) {
			throw new AmountException("amount is less then 1...");
		}
	}

	@Override
	public ResponseEntity<String> getStatusUsingPOST(@RequestBody OrderStatusRequest request) {
		
		OrderEntity orderEntity = orderDAO.findByOrderId(request.getOrderId());
		
		return new ResponseEntity<String>(orderEntity.getStatus().name(),HttpStatus.ACCEPTED);
		
	}

	@Override
	public ResponseEntity<String> deleteOrderUsingPOST(@RequestBody OrderStatusRequest request) {
		
		KafkaOrderStatus kafkaStatusObj = statusMapping(request);
		kafkaStatusObj.setStatus(OrderStatus.DELETED);
		kafkaOrderRequest.sendMessageForStatus(kafkaStatusObj);
		
		return new ResponseEntity<String>(kafkaStatusObj.getStatus().name(),HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<String> submitOrderUsingPOST(@RequestBody OrderStatusRequest request) {
		
		KafkaOrderStatus kafkaStatusObj = statusMapping(request);
		kafkaStatusObj.setStatus(OrderStatus.DELIVERY);
		kafkaOrderRequest.sendMessageForStatus(kafkaStatusObj);
		
		return new ResponseEntity<String>(kafkaStatusObj.getStatus().name(),HttpStatus.ACCEPTED);
	}
	
	private KafkaOrderStatus statusMapping(OrderStatusRequest request) {
		
		KafkaOrderStatus localkakfaStatusObj = new KafkaOrderStatus();
		
		localkakfaStatusObj.setOrderID(request.getOrderId());
		
		return localkakfaStatusObj;
	}

}
