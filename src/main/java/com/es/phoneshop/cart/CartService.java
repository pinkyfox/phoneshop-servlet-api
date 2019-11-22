package com.es.phoneshop.cart;

import com.es.phoneshop.exceptions.NotEnoughStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;

public class CartService {
    private static CartService cartService;

    private CartService() {
    }

    public static CartService getInstance() {
        if (cartService == null) {
            cartService = new CartService();
        }
        return cartService;
    }

    public Cart processRequest(HttpSession session, Product product, int quantity) throws NotEnoughStockException {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        addToCart(cart, product, quantity);
        return cart;
    }

    private void addToCart(Cart cart, Product product, int quantity) throws NotEnoughStockException {
        if (product.getStock() >= quantity) {
            cart.add(product, quantity);
        } else {
            throw new NotEnoughStockException();
        }
    }
}
