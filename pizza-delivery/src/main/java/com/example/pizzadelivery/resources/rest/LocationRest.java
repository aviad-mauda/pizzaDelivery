package com.example.pizzadelivery.resources.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="location",url="http://localhost:8081/")
public interface LocationRest {

	@GetMapping(value = "/getLocation")
	String getLocations();
	
}
