package com.example.pizzalocation.model;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;

@Table("location")
public class Location implements Serializable{

	@PrimaryKey
	private String name;
	
	public String getName() {
		return name;
	}
	
}
