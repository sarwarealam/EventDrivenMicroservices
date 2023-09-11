package com.alam.microservice.core.events;

import java.math.BigDecimal;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductCreatedEvent {
	
	private String productId;
	private  String title;
	private  BigDecimal price;
	private Integer quantity;

}
