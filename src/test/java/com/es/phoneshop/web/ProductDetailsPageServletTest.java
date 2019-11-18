package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.recentlyViewed.RecentlyViewed;
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
    private RecentlyViewedService recentlyViewedService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private Product product;

    private String id = "1";
    private String uri = "/product/" + id;
    private Product productObj = new Product();
    private Cart cart = new Cart();
    private List<Product> products = Collections.singletonList(new Product());
    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        servlet.setProductService(productService);
        servlet.setRecentlyViewedService(recentlyViewedService);
        when(request.getRequestURI()).thenReturn(uri);
    }

    @Test
    public void productFoundWhenTestDoGet() throws ServletException, IOException, ProductNotFoundException {
        when(productService.getProduct(id)).thenReturn(productObj);
        when(request.getRequestDispatcher("/WEB-INF/pages/product.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute("product", productObj);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void productNotFoundWhenTestDoGet() throws IOException, ServletException, ProductNotFoundException {
        when(productService.getProduct(id)).thenThrow(ProductNotFoundException.class);
        when(request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request, never()).setAttribute("product", productObj);
        verify(requestDispatcher).forward(request, response);
    }
}
