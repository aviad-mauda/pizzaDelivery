package com.example.pizzamenu.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pizzamenu.model.Discount;
import com.example.pizzamenu.model.DiscountsDates;
import com.example.pizzamenu.model.MenuItem;

@Service
public class MongoService {

	@Autowired
	private MongoData data;
	@Autowired 
	DiscountsDates discountsDates;

	public List<MenuItem> findAllMenuItems() {
		List<MenuItem> menu = data.findAll().stream().collect(Collectors.toList());
		
		
		for (MenuItem menuItem : menu) {
			if(isDiscount(menuItem)) {
				menuItem.setPriceAfterDiscount(menuItem.getPrice() * (1 - (float)menuItem.getDiscount() / 100));
			} else {
				menuItem.setPriceAfterDiscount(menuItem.getPrice());
			}
		}
			
		return menu;
	}

	private boolean isDiscount(MenuItem menuItem) {
		
		if(checkDates(discountsDates,menuItem)) {
			return true;
		}
		return false;
	}

	private boolean checkDates(DiscountsDates discountsDates, MenuItem menuItem) {

		for(Discount discount : discountsDates.getFileContent()) {
			if(discount.getItemId() == menuItem.get_id()) {
				
				LocalDate startDiscountdate = LocalDate.parse(discount.getDiscountStart());
				LocalDate endDiscountdate = LocalDate.parse(discount.getDiscountEnd());
				boolean isStartDateBeforeCurrent = startDiscountdate.isBefore(LocalDate.now()) || startDiscountdate.isEqual(LocalDate.now());
				boolean isEndDateAfterCurrent = endDiscountdate.isAfter(LocalDate.now()) || endDiscountdate.isEqual(LocalDate.now());
				
				if( isStartDateBeforeCurrent && isEndDateAfterCurrent ){
					return true;
				}
			}
		}
		
		return false;
		
	}
	
}
