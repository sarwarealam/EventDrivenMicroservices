package com.alam.microservice.query.rest;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alam.microservice.query.FindProductQuery;

@RestController
@RequestMapping("/products")
public class ProductsQueryController {
	
    @Autowired
	QueryGateway queryGateway;
    
    @GetMapping
	public List<ProductRestModel> getProducts(){
		
		FindProductQuery findProductQuery = new FindProductQuery();
		List<ProductRestModel> products = queryGateway.query(findProductQuery, ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
		//BeanUtils.copyProperties(findProductQuery, products);
		return products;
	}
	
}
