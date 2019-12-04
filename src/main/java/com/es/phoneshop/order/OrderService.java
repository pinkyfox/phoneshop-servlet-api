package com.es.phoneshop.order;

import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;

public class OrderService {
	private static OrderDao orderDao;
	private static ProductService productService;
	private static OrderService instance;

	private OrderService() {
	}

	public static OrderService getInstance() {
		if (instance == null) {
			instance = new OrderService();
			productService = ProductService.getInstance();
			orderDao = ArrayListOrderDao.getInstance();
		}
		return instance;
	}

	public boolean placeOrder(Order order) {
		order.getOrderItems().stream()
				.forEach(cartItem -> {
					try {
						String productId = cartItem.getProduct().getId();
						Product product = productService.getProduct(productId);
						int newStock = product.getStock() - cartItem.getQuantity();
						product.setStock(newStock);
					} catch (ProductNotFoundException e) {
						e.printStackTrace();
					}
				});
		orderDao.save(order);
		return true;
	}

	public Order getOrder(String orderId) throws OrderNotFoundException {
		return orderDao.getOrder(orderId).orElseThrow(OrderNotFoundException::new);
	}
}
