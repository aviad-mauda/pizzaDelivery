package com.example.pizzaorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.example.pizzaorder.gateways.consumers.sinks.IOrderRequestSink;
import com.example.pizzaorder.gateways.producers.sources.IOrderRequestSource;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableBinding({
	IOrderRequestSource.class,
	IOrderRequestSink.class
})
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
public class PizzaOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaOrderApplication.class, args);
	}

}
