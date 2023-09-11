package com.alam.microservice.OrderService.core.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orderlookup" )
public class OrderLookupEntity implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 267931793015005396L;

	
	@Id
	public String orderId;
	
	//@Column(unique = true)
	private String addressId;
}
