package com.example.pizzamenu.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "menu")
public class MenuItem {
	
	@Id
	private Long _id;
	private String item;
	private int price;
	private int discount;
	private float priceAfterDiscount;
	boolean isDiscount;
	
	public void setPriceAfterDiscount(float price) {
		priceAfterDiscount = price;
	}
	
	public void setIsDiscount(boolean bool) {
		isDiscount = bool;
	}
	
}
