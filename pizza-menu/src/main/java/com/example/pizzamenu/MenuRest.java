package com.example.pizzamenu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pizzamenu.dao.MongoService;
import com.example.pizzamenu.model.MenuItem;

@RestController
public class MenuRest {
	
	@Autowired
	MongoService menu;
	
	@GetMapping("/getMenu")
	public List<MenuItem> getMenu() {
		
		List<MenuItem> menuItems = menu.findAllMenuItems();
		
		return menuItems;
 	}
}
