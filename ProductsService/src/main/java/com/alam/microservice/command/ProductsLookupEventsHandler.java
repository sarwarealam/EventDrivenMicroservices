package com.alam.microservice.command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.alam.microservice.core.data.ProductLookupEntity;
import com.alam.microservice.core.data.ProductLookupRepository;
import com.alam.microservice.core.events.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductsLookupEventsHandler {
	
	private final ProductLookupRepository productLookupRepository;

	 public ProductsLookupEventsHandler(ProductLookupRepository productLookupRepository) {
     this.productLookupRepository =productLookupRepository;
		   }
	 
	@EventHandler
	public void on(ProductCreatedEvent event) {
		ProductLookupEntity productLookupEntity = new ProductLookupEntity(event.getProductId(),event.getTitle());
		
		productLookupRepository.save(productLookupEntity);
	}
}
