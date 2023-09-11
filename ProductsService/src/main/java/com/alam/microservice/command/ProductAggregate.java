package com.alam.microservice.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.alam.microservice.core.command.ReserveProductCommand;
import com.alam.microservice.core.data.events.ProductReservedEvent;
import com.alam.microservice.core.events.ProductCreatedEvent;

@Aggregate
public class ProductAggregate {
	
	
	private static final Logger log = LoggerFactory.getLogger(ProductAggregate.class);

	
	@AggregateIdentifier
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;
	
	public ProductAggregate() {
		
	}
	
   @CommandHandler
   public ProductAggregate(CreateProductCommand createProductCommand) {
		
	   //validate created product command
	   
	   if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO)<=0) {
		   throw new IllegalArgumentException("price can not be less or equal to zero");
	   }
       if(createProductCommand.getTitle() == null || createProductCommand.getTitle().isBlank()) {
	       throw new IllegalArgumentException("title can not be blank - aggregate section");

   }
       
       ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
	   BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
	   
	   AggregateLifecycle.apply(productCreatedEvent);
	   
	  // if(true) throw new Exception("if error took place in createProductCommand @CommandHander");
	   
   }
	   @CommandHandler
	   public void handle(ReserveProductCommand reserveProductCommand) {
		   if(quantity<reserveProductCommand.getQuantity()) {
			   throw new IllegalArgumentException("insuuficient number of items in stocks");
		   }
		   
		   ProductReservedEvent productReservedEvent = ProductReservedEvent.builder()
					.orderId(reserveProductCommand.getOrderId())
					.productId(reserveProductCommand.getOrderId())
					.userId(reserveProductCommand.getUserId())
					.quantity(reserveProductCommand.getQuantity())
					.build();
		   
		   AggregateLifecycle.apply(productReservedEvent);
		   
	   }
   
   @EventSourcingHandler
   public void on(ProductCreatedEvent productCreatedEvent) {
	   log.info("productCreatedEvent {}" ,productCreatedEvent);
	   this.productId = productCreatedEvent.getProductId();
	   this.quantity = productCreatedEvent.getQuantity();
	   this.price = productCreatedEvent.getPrice();
	   this.title= productCreatedEvent.getTitle();
   }
   
   @EventSourcingHandler
   public void on(ProductReservedEvent productReservedEvent) {
	   log.info("productReservedEvent {}" ,productReservedEvent);
	   this.quantity -=productReservedEvent.getQuantity();
	   
   }
   
   
   }
   

