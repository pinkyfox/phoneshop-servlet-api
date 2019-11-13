package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    private RequestDispatcher requestDispatcher;

    private String id = "1";
    private String uri = "/product/" + id;
    private Product product = new Product();
    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        servlet.setProductService(productService);
        when(request.getRequestURI()).thenReturn(uri);
    }

    @Test
    public void productFoundWhenTestDoGet() throws ServletException, IOException, ProductNotFoundException {
        when(productService.getProduct(id)).thenReturn(product);
        when(request.getRequestDispatcher("/WEB-INF/pages/product.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void productNotFoundWhenTestDoGet() throws IOException, ServletException, ProductNotFoundException {
        when(productService.getProduct(id)).thenThrow(ProductNotFoundException.class);
        when(request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request, never()).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);
    }
}
