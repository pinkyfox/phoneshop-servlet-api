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
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ProductService productService;

    @Mock
    private RequestDispatcher requestDispatcher;

    private String queryParam = "query";
    private String sortParam = "sort";
    private String orderParam = "order";
    private List<Product> result = Collections.singletonList(new Product());

    private ProductListPageServlet servlet = new ProductListPageServlet();

    @Before
    public void setup() {
        servlet.setProductService(productService);
        when(request.getParameter("query")).thenReturn(queryParam);
        when(request.getParameter("sort")).thenReturn(sortParam);
        when(request.getParameter("order")).thenReturn(orderParam);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(productService.processQueryString(queryParam, sortParam, orderParam)).thenReturn(result);
        when(request.getRequestDispatcher("/WEB-INF/pages/productList.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute("products", result);
        verify(requestDispatcher).forward(request, response);
    }
}