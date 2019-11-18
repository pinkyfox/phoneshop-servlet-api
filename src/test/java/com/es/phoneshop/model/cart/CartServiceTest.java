package com.es.phoneshop.model.cart;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.exceptions.CannotParseToIntException;
import com.es.phoneshop.exceptions.NotEnoughStockException;
import com.es.phoneshop.exceptions.ValueBelowOrEqualsZeroException;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private String cart = "cart";
    private String quantity = "quantity";
    private String correctQuantity = "1";
    private String outOfRangeQuantity = "1000";
    private String notANumberQuantity = "i'm so tired:(";
    private String invalidQuantity = "-1";
    private Cart cartObject = new Cart();
    private Product product = new Product("1L", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg" );

    private CartService cartService;
    @Before
    public void setup() {
        cartService = CartService.getInstance();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(cart)).thenReturn(cartObject);
    }

    @Test
    public void addingToCartWhenProcessRequest() throws ValueBelowOrEqualsZeroException, CannotParseToIntException, NotEnoughStockException {
        when(request.getParameter(quantity)).thenReturn(correctQuantity);
        assertEquals(cartObject, cartService.processRequest(request, product));
    }

    @Test(expected = ValueBelowOrEqualsZeroException.class)
    public void getErrorWhenProcessRequestWithQuantityBelowOrEqualsZero() throws ValueBelowOrEqualsZeroException, CannotParseToIntException, NotEnoughStockException {
        when(request.getParameter(quantity)).thenReturn(invalidQuantity);
        assertEquals(cartObject, cartService.processRequest(request, product));
    }

    @Test(expected = CannotParseToIntException.class)
    public void getErrorWhenProcessRequestWithNotANumberQuantity() throws ValueBelowOrEqualsZeroException, CannotParseToIntException, NotEnoughStockException {
        when(request.getParameter(quantity)).thenReturn(notANumberQuantity);
        assertEquals(cartObject, cartService.processRequest(request, product));
    }

    @Test(expected = NotEnoughStockException.class)
    public void getErrorWhenProcessRequestWithOutOfRangeQuantity() throws ValueBelowOrEqualsZeroException, CannotParseToIntException, NotEnoughStockException {
        when(request.getParameter(quantity)).thenReturn(outOfRangeQuantity);
        assertEquals(cartObject, cartService.processRequest(request, product));
    }
}
