package com.es.phoneshop.cart;

import com.es.phoneshop.exceptions.CartItemNotFoundException;
import com.es.phoneshop.exceptions.InvalidQuantityException;
import com.es.phoneshop.exceptions.NotANumberException;
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

    public Cart addProductToUserSessionCart(HttpSession session, Product product, int quantity) throws NotEnoughStockException {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        addToCart(cart, product, quantity);
        return cart;
    }

    public void updateCartItem(HttpSession session, String productId, String newQuantity) throws
            InvalidQuantityException, NotEnoughStockException, NotANumberException, CartItemNotFoundException {
        Cart cart = (Cart) session.getAttribute("cart");
        CartItem cartItem = findCartItem(cart, productId);
        int maxQuantity = cartItem.getProduct().getStock();
        validateQuantity(newQuantity, maxQuantity);
        cart.rewriteCartItem(cartItem, Integer.valueOf(newQuantity));
    }

    public void deleteCartItem(HttpSession session, String productId) throws CartItemNotFoundException {
        Cart cart = (Cart) session.getAttribute("cart");
        CartItem cartItem = findCartItem(cart, productId);
        cart.delete(cartItem);
    }

    public CartItem findCartItem(Cart cart, String productId) throws CartItemNotFoundException {
        return cart.findCartItem(productId).orElseThrow(CartItemNotFoundException::new);
    }

    private void validateQuantity(String quantity, int maxQuantity) throws
            InvalidQuantityException, NotEnoughStockException, NotANumberException {
        try {
            Integer value = Integer.valueOf(quantity);
            if (value <= 0) {
                throw new InvalidQuantityException();
            } else if (value > maxQuantity) {
                throw new NotEnoughStockException(String.valueOf(maxQuantity));
            }
        } catch (NumberFormatException e) {
            throw new NotANumberException();
        }
    }

    private void addToCart(Cart cart, Product product, int quantity) throws NotEnoughStockException {
        CartItem cartItem = new CartItem(product, quantity);
        int index = cart.getCartItemList().indexOf(cartItem);

        if (index >= 0) {
            CartItem previousValue = cart.getCartItemList().get(index);
            int newQuantity = previousValue.getQuantity() + quantity;
            if (newQuantity > cartItem.getProduct().getStock()) {
                int availableQuantity = cartItem.getProduct().getStock() - previousValue.getQuantity();
                throw new NotEnoughStockException(String.valueOf(availableQuantity));
            }
        }

        if (product.getStock() >= quantity) {
            cart.add(cartItem);
        } else {
            throw new NotEnoughStockException(String.valueOf(product.getStock()));
        }
    }
}
