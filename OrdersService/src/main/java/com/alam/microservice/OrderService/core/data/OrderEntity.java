package com.alam.microservice.OrderService.core.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alam.microservice.OrderService.core.model.OrderStatus;

import lombok.Data;

@Data
@Entity
@Table(name ="orders")
public class OrderEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8545933607867951355L;

	@Id
	@Column(unique = true)
	public String orderId;
	private String userId;
	private String productId;
	private int quantity;
	private String addressId;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
}
