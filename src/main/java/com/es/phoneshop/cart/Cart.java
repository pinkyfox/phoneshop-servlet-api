package com.es.phoneshop.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class Cart implements Serializable {
    private List<CartItem> cartItemList;

    public Cart() {
        cartItemList = new ArrayList<>();
    }

    public void add(CartItem cartItem) {
        if (isExist(cartItem)) {
            mergeCartItem(cartItem, cartItem.getQuantity());
        } else {
            cartItemList.add(cartItem);
        }
    }

    private boolean isExist(CartItem cartItem) {
        Optional<CartItem> isExist = cartItemList.stream()
                .filter(currentCartItem -> currentCartItem.equals(cartItem))
                .findAny();
        return isExist.isPresent();
    }

    private void mergeCartItem(CartItem cartItem, int quantity) {
        if (quantity > 0) {
            CartItem previousValue = cartItemList.get(cartItemList.indexOf(cartItem));
            int newQuantity = previousValue.getQuantity() + quantity;
            previousValue.setQuantity(newQuantity);
        } else {
            delete(cartItem);
        }
    }

    public void delete(CartItem cartItem) {
        cartItemList.removeIf(c -> cartItem.equals(c));
    }

    public Optional<CartItem> findCartItem(String productId) {
       return cartItemList.stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findAny();
    }

    public void rewriteCartItem(CartItem cartItem, int quantity) {
        if (quantity > 0) {
            CartItem previousValue = cartItemList.get(cartItemList.indexOf(cartItem));
            previousValue.setQuantity(quantity);
        } else {
            delete(cartItem);
        }
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public int getCartSize() {
        return cartItemList.size();
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItem cartItem : cartItemList) {
            BigDecimal price = cartItem.getProduct().getPrice();
            BigDecimal quantity = BigDecimal.valueOf(cartItem.getQuantity());
            totalPrice = totalPrice.add(price.multiply(quantity));
        }
        return totalPrice;
    }
}
