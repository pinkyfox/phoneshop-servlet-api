package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private Service service;

    @Override
    public void init() {
        service = new Service();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("product", service.getProduct(request.getRequestURI()
            .substring(request.getRequestURI().lastIndexOf("/") + 1)));
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }
}
