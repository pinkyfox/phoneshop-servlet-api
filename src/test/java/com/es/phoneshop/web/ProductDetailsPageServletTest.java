package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.exceptions.NotEnoughStockException;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.recentlyViewed.RecentlyViewedService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ProductService productService;

    @Mock
    private CartService cartService;

    @Mock
    private HttpSession session;

    @Mock
    private RecentlyViewedService recentlyViewedService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private Product productMock;

    private String id = "1";
    private String uri = "/product/" + id;
    private String error = "error";
    private String cartAttribute = "cart";
    private int stock = 1;
    private Product product = new Product();
    private Cart cart = new Cart();
    private List<Product> products = Collections.singletonList(new Product());
    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        servlet.setProductService(productService);
        servlet.setRecentlyViewedService(recentlyViewedService);
        servlet.setCartService(cartService);
        when(request.getRequestURI()).thenReturn(uri);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void productFoundWhenTestDoGet() throws ServletException, IOException, ProductNotFoundException {
        when(productService.getProduct(id)).thenReturn(product);
        when(request.getRequestDispatcher("/WEB-INF/pages/product.jsp")).thenReturn(requestDispatcher);
        when(recentlyViewedService.processRequest(request, product)).thenReturn(products);

        servlet.doGet(request, response);

        verify(request).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void productNotFoundWhenTestDoGet() throws IOException, ServletException, ProductNotFoundException {
        when(productService.getProduct(id)).thenThrow(ProductNotFoundException.class);
        when(request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp")).thenReturn(requestDispatcher);
        when(recentlyViewedService.processRequest(request, product)).thenReturn(products);

        servlet.doGet(request, response);

        verify(request, never()).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void productAddedToCartWhenDoPost() throws ProductNotFoundException {
        when(productService.getProduct(uri)).thenReturn(product);
        when(cartService.processRequest(request, product)).thenReturn(cart);

        servlet.doPost(request, response);

        verify(response).sendRedirect(uri + "?success=true");
    }

    @Test
    public void getProductNotFoundExceptionWhenDoPost() throws ProductNotFoundException, ValueBelowOrEqualsZeroException, CannotParseToIntException, NotEnoughStockException, IOException, ServletException {
        when(productService.getProduct(id)).thenThrow(ProductNotFoundException.class);
        when(cartService.processRequest(request, product)).thenReturn(cart);
        when(request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp")).thenReturn(requestDispatcher);

        servlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }
}
