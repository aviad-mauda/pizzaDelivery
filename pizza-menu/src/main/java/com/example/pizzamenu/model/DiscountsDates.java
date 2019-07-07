package com.example.pizzamenu.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Service
@Data
public class DiscountsDates {
	
	//@Autowired
	private List<Discount> fileContent = new ArrayList<>();
	
	//@PostContruct
	public DiscountsDates() throws IOException {
		
		fileContent = readDatesFile();
		
	}

	private ArrayList<Discount> readDatesFile() throws FileNotFoundException , IOException  {
		File file = null;
		String content = null;
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Discount> discounts;
		
		file = ResourceUtils.getFile("classpath:discounts_dates.json");
		content = new String(Files.readAllBytes(file.toPath()));
		discounts = mapper.reader().forType(new TypeReference<ArrayList<Discount>>() {}).readValue(content);
		
		return discounts;
	}
	/*
	private Discount a() {
		ObjectMapper mapper = new ObjectMapper();
		Discount staff2 = null;
        try {

            // JSON file to Java object
        	DiscountsDates staff = mapper.readValue(new File("c:\\test\\staff.json"), DiscountsDates.class);

            // JSON string to Java object
            String jsonInString = "{\"name\":\"mkyong\",\"age\":37,\"skills\":[\"java\",\"python\"]}";
            staff2 = mapper.readValue(jsonInString, Discount.class);

            // compact print
            System.out.println(staff2);

            // pretty print
            String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff2);

            System.out.println(prettyStaff1);


        } catch (IOException e) {
            e.printStackTrace();
        }
		return staff2;
	}
	*/
}
