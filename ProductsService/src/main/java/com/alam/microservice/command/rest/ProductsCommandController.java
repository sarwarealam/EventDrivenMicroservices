package com.alam.microservice.command.rest;

import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alam.microservice.command.CreateProductCommand;

@RestController
@RequestMapping("/products")
public class ProductsCommandController {
	
	
	private static final Logger log = LoggerFactory.getLogger(ProductsCommandController.class);

	
	private final Environment env;
	private final CommandGateway commandGateway;
	
	@Autowired
	public ProductsCommandController(Environment env,CommandGateway commandGateway) {
	this.env = env;
	this.commandGateway = commandGateway;
	}
	
	@PostMapping
	public String createProduct(@Valid @RequestBody CreateProductRestModel createProductRestModel) {
		log.info("createProductRestModel {}" ,createProductRestModel);
		CreateProductCommand createProductCommand = CreateProductCommand.builder()
		.title(createProductRestModel.getTitle())
		.price(createProductRestModel.getPrice())
		.quantity(createProductRestModel.getQuantity())
		.productId(UUID.randomUUID().toString()).build();
		
		
		String returnValue;
		
		 returnValue = commandGateway.sendAndWait(createProductCommand);

//		try {
//			 returnValue = commandGateway.sendAndWait(createProductCommand);
//		}
//		catch(Exception ex) {
//			returnValue = ex.getLocalizedMessage();
//		}
		
		return returnValue;
		//return "HTTP POST HANDLED " +createProductRestModel.getTitle();
	}
	
//	@GetMapping
//	public String getProduct() {
//		return "HTTP GET HANDLED : " +env.getProperty("local.server.port");
//	}
//	
//	@PutMapping
//	public String updateProduct() {
//		return "HTTP PUT HANDLED";
//	}
//	
//	
//	@DeleteMapping
//	public String deleteProduct() {
//		return "HTTP DELETE HANDLED";
//	}
	

}
