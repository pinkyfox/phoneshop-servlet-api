package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;;
import com.es.phoneshop.order.Order;
import com.es.phoneshop.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class CheckoutPageServlet extends HttpServlet {
	private OrderService orderService;

	@Override
	public void init() throws ServletException {
		orderService = OrderService.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String orderId = UUID.randomUUID().toString();

		Order order = new Order(cart);
		order.setOrderId(orderId);
		order.setSubtotal(cart.getTotalPrice());
		order.setLastName(request.getParameter("lastName"));
		order.setFirstName(request.getParameter("firstName"));
		order.setDeliveryAddress(request.getParameter("deliveryAddress"));
		order.setDate(request.getParameter("deliveryDate"));
		order.setPhoneNumber(request.getParameter("phoneNumber"));
		order.setPaymentMethod(request.getParameter("paymentMethod"));

		orderService.placeOrder(order);

		request.getSession().setAttribute("cart", new Cart());
		response.sendRedirect("http://localhost:8080/phoneshop-servlet-api/order/overview/" + orderId);
	}
}
