package com.alam.microservice.OrderService.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.alam.microservice.OrderService.core.events.OrderCreatedEvent;
import com.alam.microservice.OrderService.core.model.OrderStatus;

@Aggregate
public class OrderAggregate {

	@AggregateIdentifier
	public String orderId;
	private String userId;
	private String productId;
	private int quantity;
	private String addressId;
	private OrderStatus orderStatus;

	public OrderAggregate() {

	}

	@CommandHandler
	public OrderAggregate(CreateOrderCommand createOrderCommand) {
		
		OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
		BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
		AggregateLifecycle.apply(orderCreatedEvent);
		
		
		if(createOrderCommand.getProductId() == null || createOrderCommand.getProductId().isBlank()) {
			   throw new IllegalArgumentException("productid can not be blank - aggregate section");
		   }
		if(createOrderCommand.getQuantity()<=0) {
			   throw new IllegalArgumentException("quantity can not be less or equal to zero");
		   }
	       

	}
	
	@EventSourcingHandler
	public void on(OrderCreatedEvent orderCreatedEvent) throws Exception {
		this.orderId = orderCreatedEvent.getOrderId();
        this.productId = orderCreatedEvent.getProductId();
        this.userId = orderCreatedEvent.getUserId();
        this.addressId = orderCreatedEvent.getAddressId();
        this.quantity = orderCreatedEvent.getQuantity();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
	}
}
