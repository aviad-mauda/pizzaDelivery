package com.example.pizzadelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
<<<<<<< HEAD
import org.springframework.cloud.openfeign.EnableFeignClients;
=======
>>>>>>> master
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.example.pizzadelivery.gateways.consumers.sinks.IDeliveryKafkaConsumer;
import com.example.pizzadelivery.gateways.producers.sources.IDeliveryKafkaProducer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
<<<<<<< HEAD
@EnableFeignClients
@EnableSwagger2
@EnableBinding({
	IDeliveryKafkaProducer.class,
	IDeliveryKafkaConsumer.class
})
@EnableDiscoveryClient
=======
@EnableSwagger2	
@EnableBinding({
	IDeliveryKafkaProducer.class,
	IDeliveryKafkaConsumer.class,
})
@EnableDiscoveryClient

>>>>>>> master
public class PizzaDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaDeliveryApplication.class, args);
	}

}
