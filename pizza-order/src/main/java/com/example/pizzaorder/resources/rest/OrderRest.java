package com.example.pizzaorder.resources.rest;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.pizzaorder.api.external.BillingApi;
import com.example.pizzaorder.dao.OrderService;
import com.example.pizzaorder.exceptions.AmountException;
import com.example.pizzaorder.gateways.OrderRequestProducer;
import com.example.pizzaorder.model.OrderEntity;
import com.example.pizzaorder.model.OrderStatus;
import com.example.pizzaorder.model.external.OrderStatusRequest;
import com.example.pizzaorder.model.external.OrderEntityRequest;
@RestController
public class OrderRest implements BillingApi{

	@Autowired
	private OrderService orderService;
	@Autowired
	OrderRequestProducer kafkaOrderRequest;
	
	@Override
	public ResponseEntity<Long> createOrderUsingPOST(@RequestBody OrderEntityRequest order) {
		
		if(orderValidator(order)) {
			OrderEntity orderEntity = OrderMapping(order);
			//orderService.createOrder(orderEntity);
			kafkaOrderRequest.sendMessage(order);
			
			return new ResponseEntity<Long>((long) 0,HttpStatus.CREATED);
		}
		
		return new ResponseEntity<Long>((long) -1,HttpStatus.BAD_REQUEST);
	}

	private OrderEntity OrderMapping(OrderEntityRequest orderRequest) {
		
		OrderEntity order = null;
		
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
		
		
		String status = orderService.getStatus(request.getOrderId());

		return new ResponseEntity<String>(status,HttpStatus.ACCEPTED);
		
	}

	@Override
	public ResponseEntity<String> deleteOrderUsingPOST(@RequestBody OrderStatusRequest orderId) {
		
		String status = orderService.setStatus(OrderStatus.DELETED.name(), orderId.getOrderId());
		
		return new ResponseEntity<String>(status,HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<String> submitOrderUsingPOST(@RequestBody OrderStatusRequest orderId) {
		
		String status = orderService.setStatus(OrderStatus.DELIVERY.name(), orderId.getOrderId());
		
		return new ResponseEntity<String>(status,HttpStatus.ACCEPTED);
	}

	
}
