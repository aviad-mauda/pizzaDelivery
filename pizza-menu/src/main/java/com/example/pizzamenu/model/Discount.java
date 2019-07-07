package com.example.pizzamenu.model;

import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class Discount {

	private long itemId;
	private String discountStart;
	private String discountEnd;

}
