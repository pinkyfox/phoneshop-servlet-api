package com.es.phoneshop.order;

import com.es.phoneshop.Locker.Locker;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class ArrayListOrderDao implements OrderDao, Serializable {
	private static OrderDao instance;
	private static List<Order> orders;
	private static Locker locker;

	private ArrayListOrderDao() {
		orders = new ArrayList<>();
	}

	public static OrderDao getInstance() {
		if (instance == null) {
			locker = new Locker();
			instance = new ArrayListOrderDao();
		}
		return instance;
	}

	@Override
	public void save(Order order) {
		synchronized (locker.getLock(order.getOrderId())) {
			orders.add(order);
		}
	}

	@Override
	public Optional<Order> getOrder(String orderId) {
		return orders.stream()
				.filter(order -> orderId.equals(order.getOrderId()))
				.findAny();
	}
}
