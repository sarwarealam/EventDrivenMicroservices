package com.alam.microservice.OrderService;

import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

import com.alam.microservice.OrderService.command.interceptor.CreateOrderCommandInterceptor;

@EnableEurekaClient
@SpringBootApplication
public class OrdersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersServiceApplication.class, args);
	}
	
	@Autowired
	public void registerCreateOrderCommandInterceptor(ApplicationContext context,
			CommandBus command) {
		
		command.registerDispatchInterceptor(context.getBean(CreateOrderCommandInterceptor.class));
		
	}

}
