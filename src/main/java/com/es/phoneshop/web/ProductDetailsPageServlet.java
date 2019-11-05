package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = ProductService.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("product", productService.getProduct(request.getRequestURI()
            .substring(request.getRequestURI().lastIndexOf("/") + 1)));
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }
}
