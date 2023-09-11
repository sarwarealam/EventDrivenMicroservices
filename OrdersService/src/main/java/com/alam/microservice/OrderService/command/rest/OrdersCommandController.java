package com.alam.microservice.OrderService.command.rest;

import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alam.microservice.OrderService.command.CreateOrderCommand;
import com.alam.microservice.OrderService.core.model.OrderStatus;

@RestController
@RequestMapping("/orders")
public class OrdersCommandController {
	
	
	private final CommandGateway commandGateway;
	
	public OrdersCommandController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
		
	}
	
	String userId = "27b95829-4f3f-4ddf-8983-151ba010e35b";
	
	@PostMapping
	public String createOrders(@Valid @RequestBody CreateOrdersRestModel order) {
		
		CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
				                                .addressId(order.getAddressId())
				                                .orderId(UUID.randomUUID().toString())
				                                .productId(order.getProductId())
				                                .userId(userId)
				                                .quantity(order.getQuantity())
				                                .orderStatus(OrderStatus.CREATED).build();
		
		return commandGateway.sendAndWait(createOrderCommand);
	}

}
