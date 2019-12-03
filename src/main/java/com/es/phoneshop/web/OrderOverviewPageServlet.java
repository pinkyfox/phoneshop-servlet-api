package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
	private static OrderService orderService;

	@Override
	public void init() throws ServletException {
		orderService = OrderService.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("order", orderService.getOrder(getOrderId(request)));
		} catch (OrderNotFoundException e) {
			request.getRequestDispatcher("/WEB-INF/pages/orderNotFound.jsp").forward(request, response);
		}
		request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);
	}

	private String getOrderId(HttpServletRequest request) {

		return request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
	}
}
