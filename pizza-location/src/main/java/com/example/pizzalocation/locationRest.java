package com.example.pizzalocation;

import org.springframework.web.bind.annotation.RestController;

import com.example.pizzalocation.dao.CassandraService;
import com.example.pizzalocation.model.Location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class locationRest {
	
	@Autowired
	CassandraService locations;
	
	@GetMapping("/getLocation")
	public List<String>  getLocation() {
		
		//List<Map<UUID,String>> maps = new ArrayList<Map<UUID,String>>();
		List<String> maps = locations.findAllLocations();
		
		return maps;
 	}
}

