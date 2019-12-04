package com.es.phoneshop.Listeners;

import com.es.phoneshop.cart.Cart;

import javax.servlet.http.*;

public class UserSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setAttribute("cart", new Cart());
		se.getSession().setAttribute("isFirst", true);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		//NOP
	}
}
