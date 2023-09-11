package com.alam.microservice.command.rest;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateProductRestModel {
	
	//@NotBlank(message = "product title can not be blank")
	private String title;
	
	@Min(value = 1,message = "price can not be lower than 1")
	private BigDecimal price;
	
	@Min(value = 1,message = "quantity can not be lower than 1")
	@Max(value = 5,message = "quantity can not be larger than 6")
	private Integer quantity;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	

}
