package com.example.pizzadelivery.resources.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="menu",url="http://localhost:8082/")
public interface MenuRest {

	@GetMapping(value = "/getMenu")
	String getMenu();
	
}
