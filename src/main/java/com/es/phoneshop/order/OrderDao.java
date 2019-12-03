package com.es.phoneshop.order;

import java.util.Optional;

public interface OrderDao {
	void save(Order order);
	Optional<Order> getOrder(String orderId);
}
