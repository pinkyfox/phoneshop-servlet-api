package com.es.phoneshop.model.cart;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CartTest {
    private Cart cart;
    private List<CartItem> cartItemsList;

    @Before
    public void setup() {
        cart = new Cart();
        cartItemsList = new ArrayList<>();
        cartItemsList.add(new CartItem(new Product("1L", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg" ), 1));
        cart.setCartItemList(cartItemsList);
    }

    @Test
    public void addingToCartWhenAdd() {
        Product product = new Product();
        product.setId("test");

        cart.add(product, 100);

        List<CartItem> result = cart.getCartItemList();
        assertEquals(2, result.size());
        assertEquals("1L", result.get(0).getProduct().getId());
        assertEquals(1, result.get(0).getQuantity());
        assertEquals("test", result.get(1).getProduct().getId());
        assertEquals(100, result.get(1).getQuantity());
    }

    @Test
    public void rewriteQuantityIfItemAlreadyExistWhenAdd() {
        Product product = new Product("1L", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg" );

        cart.add(product, 100);

        List<CartItem> result = cart.getCartItemList();
        assertEquals(1, result.size());
        assertEquals("1L", result.get(0).getProduct().getId());
        assertEquals(101, result.get(0).getQuantity());
    }


}
