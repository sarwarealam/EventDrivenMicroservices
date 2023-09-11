package com.alam.microservice.OrderService.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLookupRepository extends JpaRepository<OrderLookupEntity, String> {

	OrderLookupEntity findByOrderIdOrAddressId(String orderId,String addressId);
}
