package com.alam.microservice.OrderService.command.interceptor;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alam.microservice.OrderService.command.CreateOrderCommand;
import com.alam.microservice.OrderService.core.data.OrderLookupEntity;
import com.alam.microservice.OrderService.core.data.OrderLookupRepository;

@Component
public class CreateOrderCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

	private final OrderLookupRepository orderLookupRepository;
	
	public CreateOrderCommandInterceptor(OrderLookupRepository orderLookupRepository) {
		this.orderLookupRepository = orderLookupRepository;
	}

private static final Logger log = LoggerFactory.getLogger(CreateOrderCommandInterceptor.class);

	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {
		return (index,command)->{
			
			 log.info("Command message interceptor" +command.getPayloadType());
				if(CreateOrderCommand.class.equals(command.getPayloadType())) {
					CreateOrderCommand createOrderCommand = (CreateOrderCommand)command.getPayload();
					
					
//					if(createOrderCommand.getProductId() == null || createOrderCommand.getProductId().isBlank()) {
//						   throw new IllegalArgumentException("productid can not be blank - message Interceptor section");
//					   }
//				   if(createOrderCommand.getQuantity()<=0) {
//				   throw new IllegalArgumentException("quantity can not be less or equal to zero");
//				   }
			      
					
					OrderLookupEntity OrderLookupEntity = orderLookupRepository.findByOrderIdOrAddressId(createOrderCommand.getOrderId(),createOrderCommand.getAddressId());
					
					if(OrderLookupEntity != null) {
						throw new IllegalStateException(String.format("order with orderId %s or addressId %s is already exit", createOrderCommand.getOrderId(), createOrderCommand.getAddressId()));

					}
						
				}
		return command;
				
		};
}
}
