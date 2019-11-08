package com.es.phoneshop.web;

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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private String uri = "sample";
    private Product product = new Product();
    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        servlet.setProductService(productService);
        when(request.getRequestURI()).thenReturn(uri);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(productService.getProduct(uri)).thenReturn(product);
        when(request.getRequestDispatcher("/WEB-INF/pages/product.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);
    }
}
