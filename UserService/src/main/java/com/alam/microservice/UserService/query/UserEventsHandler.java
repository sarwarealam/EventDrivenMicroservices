package com.alam.microservice.UserService.query;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.alam.microservice.core.model.PaymentDetails;
import com.alam.microservice.core.model.User;
import com.alam.microservice.core.query.FetchUserPaymentDetailsQuery;


@Component
public class UserEventsHandler {
	
	 @QueryHandler
	    public User findUserPaymentDetails(FetchUserPaymentDetailsQuery query) {
	        
	        PaymentDetails paymentDetails = PaymentDetails.builder()
	                .cardNumber("123Card")
	                .cvv("123")
	                .name("Alam Munshi")
	                .validUntilMonth(12)
	                .validUntilYear(2030)
	                .build();

	        User user = User.builder()
	                .firstName("Alam")
	                .lastName("Munshi")
	                .userId(query.getUserId())
	                .paymentDetails(paymentDetails)
	                .build();

	        return user;
	    }

}
