package com.es.phoneshop.cart;

import com.es.phoneshop.model.product.Product;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class Cart {
    private List<CartItem> cartItemList;

    public Cart() {
        cartItemList = new ArrayList<>();
    }

    public void add(Product product, int quantity) {
        CartItem newCartItem = new CartItem(product, quantity);
        if (isExist(newCartItem)) {
            rewriteCartItemParameters(newCartItem, quantity);
        } else {
            cartItemList.add(newCartItem);
        }
    }

    public void delete(CartItem cartItem) {
        cartItemList.removeIf(c -> cartItem.equals(c));
    }

    private boolean isExist(CartItem cartItem) {
        Optional<CartItem> isExist = cartItemList.stream()
                .filter(currentCartItem -> currentCartItem.equals(cartItem))
                .findAny();
        return isExist.isPresent();
    }

    private void rewriteCartItemParameters(CartItem cartItem, int quantity) {
        if (quantity != 0) {
            CartItem previousValue = cartItemList.get(cartItemList.indexOf(cartItem));
            previousValue.setQuantity(previousValue.getQuantity() + quantity);
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
}
