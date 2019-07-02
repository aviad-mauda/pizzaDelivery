package com.example.pizzalocation;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class locationRest {
	
	@GetMapping("/getLocation")
	public List<Map<UUID,String>>  getLocation() {
		
		List<Map<UUID,String>> maps = new ArrayList<Map<UUID,String>>();
		
		return maps;
 	}
}

