package com.example.pizzamenu.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Service;

import com.example.pizzamenu.model.MenuItem;

@Service
public interface MongoData extends MongoRepository<MenuItem, Long>{
}
