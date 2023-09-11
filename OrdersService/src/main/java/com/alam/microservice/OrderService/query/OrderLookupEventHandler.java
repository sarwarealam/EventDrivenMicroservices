package com.alam.microservice.OrderService.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.alam.microservice.OrderService.core.data.OrderEntity;
import com.alam.microservice.OrderService.core.data.OrderLookupEntity;
import com.alam.microservice.OrderService.core.data.OrderLookupRepository;
import com.alam.microservice.OrderService.core.events.OrderCreatedEvent;

@Component
@ProcessingGroup("order-group")
public class OrderLookupEventHandler {
	
private final OrderLookupRepository orderLookupRepository;

public OrderLookupEventHandler(OrderLookupRepository orderLookupRepository) {
	this.orderLookupRepository = orderLookupRepository;
}

@EventHandler
public void on(OrderCreatedEvent orderCreatedEvent) throws Exception {
	
	OrderLookupEntity orderLookupEntity =new OrderLookupEntity();
	BeanUtils.copyProperties(orderCreatedEvent, orderLookupEntity);
	orderLookupRepository.save(orderLookupEntity);
	
}


}
