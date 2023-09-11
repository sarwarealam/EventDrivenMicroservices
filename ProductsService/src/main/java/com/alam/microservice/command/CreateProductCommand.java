package com.alam.microservice.command;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateProductCommand { //read only class
	
	@TargetAggregateIdentifier
	private final String productId;
	private final String title;
	private final BigDecimal price;
	private final Integer quantity;


}
