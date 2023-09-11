package com.alam.microservice.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.alam.microservice.core.data.ProductEntity;
import com.alam.microservice.core.data.ProductRepository;
import com.alam.microservice.query.rest.ProductRestModel;

@Component
public class ProductsQueryHandler {
	
	private final ProductRepository productRepository;
	
	public ProductsQueryHandler(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
@QueryHandler	
public List<ProductRestModel> findproducts(FindProductQuery query){
	
	List<ProductRestModel> productRest = new ArrayList<>();
	List<ProductEntity> storedProducts = productRepository.findAll();
	
	for (ProductEntity productEntity : storedProducts) {

		ProductRestModel productRestModel = new ProductRestModel();
		BeanUtils.copyProperties(productEntity, productRestModel);
		productRest.add(productRestModel);
		
	}
	
	//BeanUtils.copyProperties(storedProducts, productRest);
	
	return productRest;
	
}
}
