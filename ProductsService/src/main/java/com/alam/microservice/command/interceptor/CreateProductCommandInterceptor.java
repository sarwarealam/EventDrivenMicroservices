package com.alam.microservice.command.interceptor;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alam.microservice.command.CreateProductCommand;
import com.alam.microservice.core.data.ProductLookupEntity;
import com.alam.microservice.core.data.ProductLookupRepository;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>>{

	private final ProductLookupRepository productLookupRepository;
	
	public CreateProductCommandInterceptor(ProductLookupRepository productLookupRepository) {
		this.productLookupRepository = productLookupRepository;
	}
	
private static final Logger log = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {
		// TODO Auto-generated method stub
		return (index,command)->{
          log.info("Command message interceptor" +command.getPayloadType());
			if(CreateProductCommand.class.equals(command.getPayloadType())) {
				CreateProductCommand createProductCommand = (CreateProductCommand)command.getPayload();
//				  if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO)<=0) {
//					   throw new IllegalArgumentException("price can not be less or equal to zero");
//				   }
//			       if(createProductCommand.getTitle() == null || createProductCommand.getTitle().isBlank()) {
//				   throw new IllegalArgumentException("title can not be blank - message Interceptor section");
//
//			   }
				
				ProductLookupEntity productLookupEntity =	productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(), createProductCommand.getTitle());
				if(productLookupEntity != null) {
					throw new IllegalStateException(String.format("product with productId %s or title %s is already exit", createProductCommand.getProductId(), createProductCommand.getTitle()));
					
				}
			}
			return command;
		};
	}

}
