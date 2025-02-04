package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = ProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        request.setAttribute("products", productService.processQueryString(query, sort, order));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
