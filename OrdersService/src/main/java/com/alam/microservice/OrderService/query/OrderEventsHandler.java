package com.alam.microservice.OrderService.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.alam.microservice.OrderService.core.data.OrderEntity;
import com.alam.microservice.OrderService.core.data.OrderRepository;
import com.alam.microservice.OrderService.core.events.OrderCreatedEvent;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {
	
	private final OrderRepository  orderRepository;
	
	public OrderEventsHandler(OrderRepository  orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@EventHandler
	public void on(OrderCreatedEvent orderCreatedEvent) throws Exception {
		
		OrderEntity orderEntity =new OrderEntity();
		BeanUtils.copyProperties(orderCreatedEvent, orderEntity);
		orderRepository.save(orderEntity);
		
	}

}
