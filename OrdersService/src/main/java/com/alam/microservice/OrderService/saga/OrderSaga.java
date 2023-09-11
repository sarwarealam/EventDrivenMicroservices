package com.alam.microservice.OrderService.saga;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alam.microservice.OrderService.core.events.OrderCreatedEvent;
import com.alam.microservice.core.command.ReserveProductCommand;
import com.alam.microservice.core.data.events.ProductReservedEvent;
import com.alam.microservice.core.model.User;
import com.alam.microservice.core.query.FetchUserPaymentDetailsQuery;

@Saga
public class OrderSaga {
	
	
	
	private static final Logger log = LoggerFactory.getLogger(OrderSaga.class);

	@Autowired
	private transient CommandGateway commandGateway;

	@Autowired
	private transient QueryGateway queryGateway;
	
	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderCreatedEvent orderCreatedEvent) {
		
		ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
				.orderId(orderCreatedEvent.getOrderId())
				.productId(orderCreatedEvent.getOrderId())
				.userId(orderCreatedEvent.getUserId())
				.quantity(orderCreatedEvent.getQuantity())
				.build();
		
		log.info("OrderCreatedEvent for order id " +orderCreatedEvent.getOrderId() +" for product id " + orderCreatedEvent.getProductId());
		
		commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand,Object>(){
			@Override
			public void onResult(CommandMessage<? extends ReserveProductCommand> commandMessage,
					CommandResultMessage<? extends Object> commandResultMessage) {

				if(commandResultMessage.isExceptional()) {
					// strat a compensating transaction
				}
			}

		});
	}
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(ProductReservedEvent productReservedEvent) {   
		log.info("ProductReservedEvent for order id " +productReservedEvent.getOrderId() +" for product id " + productReservedEvent.getProductId());
           FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery = new FetchUserPaymentDetailsQuery(productReservedEvent.getUserId());
           User userPaymentDetails = null;
           try {
        	   userPaymentDetails = queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
           }
           catch(Exception ex) {
        	   log.error(ex.getMessage());
        	   //start compensating transaction
        	   return;
           }
           if(userPaymentDetails == null) {
        	   //start compensating transaction
        	   return;
           }
           log.info("successfully fetched userpayment details for user " + userPaymentDetails.getFirstName());
	}
}