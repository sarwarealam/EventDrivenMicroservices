package com.alam.microservice.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.alam.microservice.core.data.ProductEntity;
import com.alam.microservice.core.data.ProductRepository;
import com.alam.microservice.core.data.events.ProductReservedEvent;
import com.alam.microservice.core.events.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

	private static final Logger log = LoggerFactory.getLogger(ProductEventsHandler.class);

	private final ProductRepository productRepository;

	public ProductEventsHandler(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@ExceptionHandler(resultType = Exception.class)
	public void handle(Exception exception) throws Exception{
		throw exception;
	}
	
	@ExceptionHandler(resultType = IllegalArgumentException.class)
	public void handle(IllegalArgumentException exception) throws Exception{
	}
	

	@EventHandler
	public void on(ProductCreatedEvent event) throws Exception {
		log.info("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");

		log.info("event {}", event);
		ProductEntity productEntity = new ProductEntity();
		BeanUtils.copyProperties(event, productEntity);
		try {
			productRepository.save(productEntity);
		} catch (IllegalArgumentException ex) {

		}
		
		//if(true) throw new Exception("if error took place in product event handler @eventHander");
	}
	
	@EventHandler
	public void on(ProductReservedEvent productReservedEvent) throws Exception {
		
		log.info("hellloooooooooooooooooooooooooooooooo");
		ProductEntity productEntity = productRepository.findByProductId(productReservedEvent.getProductId());
	productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
	productRepository.save(productEntity);
	log.info("ProductReservedEvent for order id " +productReservedEvent.getOrderId() +" for product id " + productReservedEvent.getProductId());
	}

}
