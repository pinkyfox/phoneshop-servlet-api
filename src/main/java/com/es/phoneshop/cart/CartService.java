package com.es.phoneshop.cart;

import com.es.phoneshop.exceptions.CannotParseToIntException;
import com.es.phoneshop.exceptions.NotEnoughStockException;
import com.es.phoneshop.exceptions.ValueBelowOrEqualsZeroException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CartService {
    private static CartService cartService = null;
    private Cart cart = null;

    private CartService() {
    }

    public static CartService getInstance() {
        if (cartService == null) {
            cartService = new CartService();
        }
        return cartService;
    }

    private void addToCart(Product product, int quantity) throws NotEnoughStockException{
        int availableStock = product.getStock();
        if (availableStock - quantity >= 0) {
            cart.add(product, quantity);
        } else {
            throw new NotEnoughStockException();
        }
    }

    public Cart processRequest(HttpServletRequest request, Product product) throws CannotParseToIntException, NotEnoughStockException, ValueBelowOrEqualsZeroException {
        HttpSession session = request.getSession();
        String quantity = request.getParameter("quantity");
        setCart((Cart) session.getAttribute("cart"));
        if (cart == null) {
            cart = new Cart();
        }
        addToCart(product, Validator.parseToInteger(quantity));
        return cart;
    }

    private void remove(CartItem cartItem) {
        cart.delete(cartItem);
    }

    private void setCart(Cart cart) {
        this.cart = cart;
    }

    private Cart getCart() {
        return cart;
    }
}
