package com.es.phoneshop.Listeners;

import com.es.phoneshop.cart.Cart;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SetUserCartSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setAttribute("cart", new Cart());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

	}
}
