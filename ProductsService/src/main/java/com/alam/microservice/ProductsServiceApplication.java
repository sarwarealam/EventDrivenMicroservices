package com.alam.microservice;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

import com.alam.microservice.command.interceptor.CreateProductCommandInterceptor;
import com.alam.microservice.core.errorhandling.ProductsServiceEventsErrorHandler;

@EnableEurekaClient
@SpringBootApplication
public class ProductsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsServiceApplication.class, args);
	}
	
	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext context,
			CommandBus commandBus) {
		
		commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class)) ;
		
	}
	
	@Autowired
	public void configure(EventProcessingConfigurer config) {
		config.registerListenerInvocationErrorHandler("Product-group", cong -> new ProductsServiceEventsErrorHandler());
	}

}
