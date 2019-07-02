package com.example.pizzalocation.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pizzalocation.model.Location;

@Service
public class CassandraService{

	@Autowired
	private CassandraData data;

	public List<String> findAllLocations() {
		
		List<String> result = new ArrayList<>();
		List<Location> locations = new ArrayList<>();
		
		data.findAll().forEach(locations::add);
		
		locations.forEach(
				(n) -> result.add(n.getName())
		);
		
		return result;
	}

}
