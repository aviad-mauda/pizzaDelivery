package com.example.pizzalocation.dao;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Service;

import com.example.pizzalocation.model.Location;

@Service
public interface CassandraData extends CassandraRepository<Location,UUID>{
	
}
