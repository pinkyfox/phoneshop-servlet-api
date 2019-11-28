package com.es.phoneshop.web;

import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.exceptions.CartItemNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {

	private CartService cartService;

	@Override
	public void init() {
		cartService = CartService.getInstance();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String productId = request.getParameter("productId");
		try {
			cartService.deleteCartItem(session, productId);
			request.setAttribute("isDeleteSuccessfully", true);
		} catch (CartItemNotFoundException e) {
			request.setAttribute("isDeleteSuccessfully", false);
		}
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
	}
}
